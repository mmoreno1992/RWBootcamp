package com.mmoreno.pokeapp

import androidx.paging.PageKeyedDataSource
import com.mmoreno.pokeapp.model.PokeEntity
import com.mmoreno.pokeapp.networking.buildApiService

class PokeDataSource() :
    PageKeyedDataSource<String, PokeEntity>() {

    private val pokeAPI = buildApiService()

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, PokeEntity>
    ) {
        val body = pokeAPI.listPokemons().execute().body()
        callback.onResult(body?.results ?: listOf(), body?.previous, body?.next)
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, PokeEntity>
    ) {
        val tempMap = handleKey(params.key)
        val bodyResult =
            pokeAPI.listPokemons(tempMap["offset"]!!, tempMap["limit"]!!).execute().body()
        callback.onResult(bodyResult?.results ?: listOf(), bodyResult?.next)
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, PokeEntity>
    ) {
        val tempMap = handleKey(params.key)
        val bodyResul =
            pokeAPI.listPokemons(tempMap["offset"]!!, tempMap["limit"]!!).execute().body()
        callback.onResult(bodyResul?.results ?: listOf(), bodyResul?.previous)
    }


    private fun handleKey(key: String): MutableMap<String, String> {
        val (_, queryPart) = key.split("?")
        val processQueries =
            queryPart.split("&".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val tempMap = mutableMapOf<String, String>()
        for (query in processQueries) {
            val (k, v) = query.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            tempMap[k] = v
        }
        return tempMap
    }
}