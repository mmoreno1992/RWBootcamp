package com.mmoreno.pokeapp.networking

import com.mmoreno.pokeapp.model.PokeEntity


/**
 * Class representing a network response from the Poke API
 * @param count number of records
 * @param previous link to the previous page
 * @param next link to the next page
 * @param results retrieved in the current http request
 */
data class PokeResponse(
    val count: Int,
    val previous: String,
    val next: String,
    val results: List<PokeEntity>
)