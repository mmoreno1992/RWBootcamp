package com.mmoreno.favmovies.model

import java.time.LocalDate
import java.util.*

open class Movie(
    val id: Int,
    val releaseDate: Date,
    val title: String,
    val summary: Int,
    val poster: Int
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