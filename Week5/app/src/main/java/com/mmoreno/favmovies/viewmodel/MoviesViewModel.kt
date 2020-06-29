package com.mmoreno.favmovies.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mmoreno.favmovies.model.Movie
import com.mmoreno.favmovies.model.MovieRepository

/**
 * Custom ViewModel for interacting with the View and our Model components
 * Delegates the persistence to the Repository object
 * @author [Mario]
 */
class MoviesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MovieRepository = MovieRepository()
    private val movies: LiveData<MutableList<Movie>>

    init {
        movies = repository.getAll();
    }

    /**
     * Method for adding a movie
     * @param movie instance of [Movie] to be saved
     */
    fun addMovie(movie: Movie) = repository.add(movie)

    /**
     * Method for deleting a movie
     * @param movie instance of [Movie] to be deleted
     */
    fun deleteMovie(movie: Movie) = repository.delete(movie)

    /**
     * Method for getting all the saved movies
     * @return [LiveData]
     */
    fun getAllMovies() = movies

    /**
     * Method for updating a movie
     * @param movie instance of [Movie] to be updated
     */
    fun updateMovie(movie: Movie) = repository.update(movie)

    /**
     * Method for finding a movie based on its unique ID
     * @param id unique instance that identifies a movie record
     * @return [LiveData]
     */
    fun findById(id: Int) = repository.findById(id)

}