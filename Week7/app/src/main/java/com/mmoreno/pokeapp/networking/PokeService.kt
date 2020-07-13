package com.mmoreno.pokeapp.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface to map our HTTP operations in the app
 */
interface PokeService {

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }

    @GET("pokemon/")
    fun listPokemons(): Call<PokeResponse>

    @GET("pokemon/")
    fun listPokemons(
        @Query("offset") offset: String,
        @Query("limit") limit: String
    ): Call<PokeResponse>
}