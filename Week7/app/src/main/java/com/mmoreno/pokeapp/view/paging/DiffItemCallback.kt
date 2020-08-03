package com.mmoreno.pokeapp.view.paging

import androidx.recyclerview.widget.DiffUtil
import com.mmoreno.pokeapp.model.PokeEntity

/**
 * Custom class implementing DiffUtil.ItemCallback
 * for comparing PokemonResult (pokemon records) items in the list
 */
class DiffItemCallback : DiffUtil.ItemCallback<PokeEntity>() {
    /**
     * Check if items are the same based on its url this is the primary key also in the database
     */
    override fun areItemsTheSame(oldItem: PokeEntity, newItem: PokeEntity): Boolean =
        oldItem.url == newItem.url

    /**
     * Checking if contents are the same, using name and url
     */
    override fun areContentsTheSame(oldItem: PokeEntity, newItem: PokeEntity): Boolean =
        oldItem.name == newItem.name && oldItem.url == newItem.url
}