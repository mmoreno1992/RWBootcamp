package com.mmoreno.pokeapp.ui.paging

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.mmoreno.pokeapp.PokeApp
import com.mmoreno.pokeapp.model.PokeDatabase
import com.mmoreno.pokeapp.model.PokeEntity
import com.mmoreno.pokeapp.networking.PokeResponse
import com.mmoreno.pokeapp.util.PagingRequestHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

/**
 * Custom Callback for interacting with the paging feature
 * This is used for deciding when and from where load the data
 * based on the interaction of the user with the view
 */
class PokeBoundaryCallback(
    private val db: PokeDatabase,
    private val loaderNotification: MutableLiveData<Int>,
    private val viewModelScope: CoroutineScope
) :
    PagedList.BoundaryCallback<PokeEntity>() {

    /**
     * api Service for interacting in the web using Retrofit
     */
    private val api = PokeApp.apiService
    private val executor = Executors.newSingleThreadExecutor()
    //An executor is needed for retrying operations
    private val helper = PagingRequestHelper(executor)
    private var mPrevious: String? = null
    private var mNext: String? = null

    /**
     * When we couldn't load any item
     */
    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        /**
         * Creating the network requests leveraging Retrofit threading management
         */
        loaderNotification.postValue(View.VISIBLE)
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { helperCallback ->

            viewModelScope.launch {
                try {
                    val request = api.listPokemons(next = mNext)
                    requestMoreData(request, helperCallback)
                } catch (ex: Exception) {
                    helperCallback.recordFailure(ex)
                }
                loaderNotification.postValue(View.GONE)
            }

        }
    }

    /**
     * Where we load the last item, we need to make another request using Retrofit
     * course is there are no records in the dadtabase
     */
    override fun onItemAtEndLoaded(itemAtEnd: PokeEntity) {
        super.onItemAtEndLoaded(itemAtEnd)
        loaderNotification.postValue(View.VISIBLE)
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) { helperCallback ->

            viewModelScope.launch {
                try {
                    val request = api.listPokemons(offset = itemAtEnd.getIdFromUrl().toInt())
                    requestMoreData(request, helperCallback)
                } catch (ex: Exception) {
                    helperCallback.recordFailure(ex)
                }
                loaderNotification.postValue(View.GONE)
            }

        }
    }

    /**
     * Method for getting the results from the http request
     * it is a suspendable function
     */
    private suspend fun requestMoreData(
        request: PokeResponse,
        helperCallback: PagingRequestHelper.Request.Callback
    ) {
        val records = request.results
        mPrevious = request.previous
        mNext = request.next
        if (records.isNotEmpty()) {
            db.pokeDao().insert(records)
            helperCallback.recordSuccess()
        }
    }

    /**
     * Interface defined for changing the visibility of the
     * ProgressBar
     */
    interface RecyclerViewInteractionListener {
        fun changeLoadingVisibility(visibility: Int)
    }
}
