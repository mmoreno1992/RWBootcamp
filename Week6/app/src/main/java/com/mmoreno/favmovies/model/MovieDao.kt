package com.mmoreno.favmovies.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Dao inheriting from [BaseDao]
 * For interacting with the Movie Table Database
 */
@Dao
interface MovieDao : BaseDao<Movie> {
    /**
     * Custom method for retrieving all the movies stored in the table
     * @return [LiveData]
     */
    @Query("SELECT ID, RELEASE_DATE, TITLE, SUMMARY, POSTER, GENRE, IS_FAVORITE, RATING FROM Movie")
    fun getMovies(): LiveData<MutableList<Movie>>

    /**
     * Custom method for retrieving a specific movie record
     * @param [movieId] id of the movie record to return
     * @return [LiveData]
     */
    @Query("SELECT ID, RELEASE_DATE, TITLE, SUMMARY, POSTER, GENRE, IS_FAVORITE, RATING FROM Movie Where ID = :movieId")
    fun findById(movieId: Int): LiveData<Movie>

    /**
     * Method for inserting all initial movies
     * @param movies list of [Movie]
     */
    @Insert
    fun insertAll(vararg movies: Movie)
}