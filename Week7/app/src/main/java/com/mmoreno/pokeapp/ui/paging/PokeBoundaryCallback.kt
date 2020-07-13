package com.mmoreno.pokeapp.ui.paging

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.mmoreno.pokeapp.model.PokeDatabase
import com.mmoreno.pokeapp.model.PokeEntity
import com.mmoreno.pokeapp.networking.PokeResponse
import com.mmoreno.pokeapp.networking.buildApiService
import com.mmoreno.pokeapp.util.PagingRequestHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

/**
 * Custom Callback for interacting with the paging feature
 * This is used for deciding when and from where load the data
 * based on the interaction of the user with the view
 */
class PokeBoundaryCallback(
    private val db: PokeDatabase,
    private val loaderNotification: MutableLiveData<Int>
) :
    PagedList.BoundaryCallback<PokeEntity>() {

    /**
     * api Service for interacting in the web using Retrofit
     */
    private val api = buildApiService()
    private val executor = Executors.newSingleThreadExecutor()
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
            api.listPokemons(next = mNext)
                .enqueue(object : Callback<PokeResponse> {
                    override fun onFailure(call: Call<PokeResponse>?, t: Throwable) {
                        loaderNotification.postValue(View.GONE)
                        helperCallback.recordFailure(t)
                    }

                    /**
                     * If there is a response insert the Poke Records :D
                     * Sorry I overused the "poke" word
                     */
                    override fun onResponse(
                        call: Call<PokeResponse>?,
                        response: Response<PokeResponse>
                    ) {
                        val records = response.body()?.results
                        mPrevious = response.body()?.previous
                        mNext = response.body()?.next
                        /**
                         * Leveraging the use of executors for managing the interaction with
                         * the database in worker thread
                         */
                        executor.execute {
                            if (records?.size ?: 0 > 0) {
                                db.pokeDao().insert(records ?: listOf())
                                helperCallback.recordSuccess()
                            }
                        }
                        loaderNotification.postValue(View.GONE)
                    }
                })
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
            api.listPokemons(offset = itemAtEnd.getIdFromUrl().toInt())
                .enqueue(object : Callback<PokeResponse> {

                    override fun onFailure(call: Call<PokeResponse>?, t: Throwable) {
                        loaderNotification.postValue(View.GONE)
                        helperCallback.recordFailure(t)
                    }

                    override fun onResponse(
                        call: Call<PokeResponse>?,
                        response: Response<PokeResponse>
                    ) {
                        val records = response.body()?.results
                        mPrevious = response.body()?.previous
                        mNext = response.body()?.next
                        executor.execute {
                            db.pokeDao().insert(records ?: listOf())
                            helperCallback.recordSuccess()
                        }
                        loaderNotification.postValue(View.GONE)

                    }
                })
        }
    }

    interface RecyclerViewInteractionListener {
        fun changeLoadingVisibility(visibility: Int)
    }
}
