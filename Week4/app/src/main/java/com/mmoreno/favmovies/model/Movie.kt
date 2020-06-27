package com.mmoreno.favmovies.model

import java.util.*

/**
 * Model data class for storing information of every Movie
 */
data class Movie(
    val id: Int,
    val releaseDate: Date,
    val title: Int,
    val summary: Int,
    val poster: Int,
    val genre:Int
) {
    var isFavorite: Boolean = false
    var rating: Float = 0.0F

    override fun equals(other: Any?): Boolean {
        if (other is Movie)
            return other.id == this.id
        return false
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}