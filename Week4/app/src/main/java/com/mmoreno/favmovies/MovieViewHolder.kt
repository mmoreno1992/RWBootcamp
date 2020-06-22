package com.mmoreno.favmovies

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_movie_list.view.*


class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    init {
        view.genreLabel
    }
    fun bind(){
        itemView.genreLabel.text="hola"
    }
}