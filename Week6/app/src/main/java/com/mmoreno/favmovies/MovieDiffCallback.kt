package com.mmoreno.favmovies

import androidx.recyclerview.widget.DiffUtil
import com.mmoreno.favmovies.model.Movie

/**
 * Utility class extending DiffCallback to compare to different lists
 * to be used in the RecyclerView
 */
class MovieDiffCallback :
    DiffUtil.ItemCallback<Movie>() {
    /**
     * Checking if items are the same based on an Id
     */
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id

    /**
     * Checking if contents are the same
     */
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.isFavorite == newItem.isFavorite &&
                oldItem.rating == newItem.rating &&
                oldItem.title == newItem.title

    }

}
