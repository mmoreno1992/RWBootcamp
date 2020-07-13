package com.mmoreno.pokeapp

import androidx.recyclerview.widget.DiffUtil
import com.mmoreno.pokeapp.model.PokeEntity

/**
 * Custom class implementing DiffUtil.ItemCallback
 * for comparing PokemonResult (pokemon records) items in the list
 */
class DiffItemCallback : DiffUtil.ItemCallback<PokeEntity>() {
    override fun areItemsTheSame(oldItem: PokeEntity, newItem: PokeEntity): Boolean =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: PokeEntity, newItem: PokeEntity): Boolean =
        oldItem.name == newItem.name && oldItem.url == newItem.url
}