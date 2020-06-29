package com.mmoreno.favmovies.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Entity class for storing information of every Movie
 * Added @ColumnInfo to every column to make all column names
 * uppercase and separate every word by a _
 */
@Entity(tableName = "Movie")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    val id: Int,
    @ColumnInfo(name = "RELEASE_DATE")
    val releaseDate: Date,
    @ColumnInfo(name = "TITLE")
    val title: String,
    @ColumnInfo(name = "SUMMARY")
    val summary: String,
    @ColumnInfo(name = "POSTER")
    val poster: Int,
    @ColumnInfo(name = "GENRE")
    val genre: String
) {
    @ColumnInfo(name = "IS_FAVORITE")
    var isFavorite: Boolean = false

    @ColumnInfo(name = "RATING")
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