package com.mmoreno.pokeapp.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mmoreno.pokeapp.model.PokeEntity
import com.mmoreno.pokeapp.model.PokeRepository
import com.mmoreno.pokeapp.view.paging.PokeBoundaryCallback

/**
 * ViewModel for interacting and retrieving the information
 * in the previous Week I was extending AndroidViewModel
 * but in this Week's assignment I'm using just 'ViewModel'
 */
class MainViewModel(private val pokeRepository: PokeRepository) : ViewModel() {

    /**
     * MutableLiveData instance for observing and changing the UI
     * according it
     */
    private val loadingFlag: MutableLiveData<Int> = MutableLiveData()

    /**
     * Creating a variable for holding the LiveData
     * and after expose it through a method
     */
    private val pokeList: LiveData<PagedList<PokeEntity>>


    init {
        loadingFlag.postValue(View.GONE)
        pokeList = initializedPagedListBuilder().build()
    }

    /**
     * Initialize and build the PagedList
     * that can be converted to LiveData
     * This function allows to observe the LiveData
     */
    private fun initializedPagedListBuilder():
            LivePagedListBuilder<Int, PokeEntity> {

        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()

        val livePageListBuilder = LivePagedListBuilder(
            pokeRepository.pokeRecords(),
            config
        )
        livePageListBuilder.setBoundaryCallback(
            PokeBoundaryCallback(
                pokeRepository, loadingFlag,
                viewModelScope
            )
        )
        return livePageListBuilder
    }

    /**
     * Custom method for exposing the LiveData object that wraps
     * our List of PokeEntities :D
     */
    fun getListOfPokemon() = pokeList

    /**
     * Custom method for exposing the MutableLiveData variable
     * that is used for changing the visibility state of the progress bar in the UI
     */
    fun getLoadingFlag() = loadingFlag

}