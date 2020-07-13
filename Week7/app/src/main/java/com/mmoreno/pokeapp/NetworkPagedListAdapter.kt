package com.mmoreno.pokeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mmoreno.pokeapp.model.PokeEntity
import kotlinx.android.synthetic.main.item_pokemon_recyclerview.view.*

/**
 * Custom PagedListAdapter for managing the items in the RecyclerView
 */
class NetworkPagedListAdapter :
    PagedListAdapter<PokeEntity, NetworkPagedListAdapter.ViewHolder>(DiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_pokemon_recyclerview,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: PokeEntity) {
            itemView.title.text = item.name
            println("----------------- url es : ${item.url}  " +
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/")
            Glide.with(itemView.context)
                .load(item.url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(itemView.imageViewPokemon);

        }
    }
}