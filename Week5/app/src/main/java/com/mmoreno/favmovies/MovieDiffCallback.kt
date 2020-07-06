package com.mmoreno.favmovies

import androidx.recyclerview.widget.DiffUtil
import com.mmoreno.favmovies.model.Movie

/**
 * Utility class extending DiffCallback to compare to different lists
 * to be used in the RecyclerView
 */
class MovieDiffCallback(
    private val oldList: MutableList<Movie>,
    private val newList: MutableList<Movie>
) :
    DiffUtil.Callback() {

    /**
     * Compare items based on its id
     * @return [Boolean] if items are the same
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    /**
     * Method to return the size of the old list
     * @return [Int] size of the old list
     */
    override fun getOldListSize() = oldList.size

    /**
     * Method to return the size of the new list
     * @return [Int] size of the new list
     */
    override fun getNewListSize() = newList.size


    /**
     * Method to compare contents in items
     * @param oldItemPosition position of the old item
     * @param newItemPosition position of the new item
     * @return [Boolean]
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovie = oldList[oldItemPosition]
        val newMovie = newList[newItemPosition]

        return oldMovie.title == newMovie.title
    }

}
