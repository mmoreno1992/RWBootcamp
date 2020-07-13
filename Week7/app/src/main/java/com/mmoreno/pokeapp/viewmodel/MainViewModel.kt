package com.mmoreno.pokeapp.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mmoreno.pokeapp.model.PokeDatabase
import com.mmoreno.pokeapp.model.PokeEntity
import com.mmoreno.pokeapp.ui.paging.PokeBoundaryCallback

/**
 * ViewModel for interacting and retrieving the information
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * MutableLiveData instance for observing and changing the UI
     * according it
     */
    val showLoading: MutableLiveData<Int> = MutableLiveData()

    init {
        showLoading.postValue(View.GONE)
    }

    /**
     * Initialize and build the PagedList
     * that can be converted to LiveData
     */
    fun initializedPagedListBuilder():
            LivePagedListBuilder<Int, PokeEntity> {

        val config = PagedList.Config.Builder()
            .setPageSize(40)
            .setEnablePlaceholders(false)
            .build()

        val database = PokeDatabase.create(getApplication())
        val livePageListBuilder = LivePagedListBuilder(
            database.pokeDao().pokeRecords(),
            config
        )
        livePageListBuilder.setBoundaryCallback(
            PokeBoundaryCallback(
                database, showLoading
            )
        )

        return livePageListBuilder
    }


}