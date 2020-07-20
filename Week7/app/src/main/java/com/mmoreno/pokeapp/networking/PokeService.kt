package com.mmoreno.pokeapp.networking

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface to map our HTTP operations in the app
 */
interface PokeService {

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }

    /**
     * Get request of the PokeAPI
     * I decided to retrieve 20 records by default
     * In this week added support for Coroutines
     */
    @GET("pokemon/")
    suspend fun listPokemons(
        @Query("offset") offset: Int? = 0,
        @Query("limit") limit: Int? = 20,
        @Query("previous") previous: String? = null,
        @Query("next") next: String? = null
    ): PokeResponse
}