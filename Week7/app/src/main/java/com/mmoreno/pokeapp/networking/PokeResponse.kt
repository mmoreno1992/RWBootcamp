package com.mmoreno.pokeapp.networking

import com.mmoreno.pokeapp.model.PokeEntity


data class PokeResponse(
    val count: Int,
    val previous: String,
    val next: String,
    val results: List<PokeEntity>
)