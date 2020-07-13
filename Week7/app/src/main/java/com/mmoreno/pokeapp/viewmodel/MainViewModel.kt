package com.mmoreno.pokeapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mmoreno.pokeapp.PokeDataSource
import com.mmoreno.pokeapp.model.PokeEntity

/**
 * ViewModel for interacting and retrieving the information
 *
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    fun createLiveData(): LiveData<PagedList<PokeEntity>> {
        val config = PagedList.Config.Builder()
            .setInitialLoadSizeHint(40)
            .setPageSize(40)
            .setPrefetchDistance(10)
            .build()

        return LivePagedListBuilder(object : DataSource.Factory<String, PokeEntity>() {
            override fun create(): DataSource<String, PokeEntity> {
                return PokeDataSource()
            }
        }, config).build()
    }
}