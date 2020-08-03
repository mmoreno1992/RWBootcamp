package com.mmoreno.pokeapp.view.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mmoreno.pokeapp.R
import com.mmoreno.pokeapp.model.PokeEntity
import kotlinx.android.synthetic.main.item_pokemon_recyclerview.view.*

/**
 * Custom PagedListAdapter for managing the items in the RecyclerView
 * leveraging Paging Library
 */
class PokePagedListAdapter :
    PagedListAdapter<PokeEntity, PokePagedListAdapter.ViewHolder>(
        DiffItemCallback()
    ) {

    /**
     * Overriding onCreateViewHOlder for inflating my custom view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_pokemon_recyclerview,
                parent,
                false
            )
        )

    /**
     * Overriding  onBindViewHolder to show
     * my Custom data
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    /**
     * Custom ViewHolder for showing an item in the RecyclerView
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * Custom method for binding every item in the list with the view
         * represented in the ViewHolder
         * Here I use Glide to caching and displaying the images
         */
        fun bind(item: PokeEntity) {
            itemView.title.text = item.name
            /**
             * Sorry I hardcoded the parameter in "load", :( I didn't have enough time
             * to make it elegant, this assignment was hard to me
             */
            Glide.with(itemView.context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${item.getIdFromUrl()}.png")
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(itemView.imageViewPokemon);
        }
    }


}