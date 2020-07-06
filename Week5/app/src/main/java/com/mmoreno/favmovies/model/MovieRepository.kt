package com.mmoreno.favmovies.model

import androidx.lifecycle.LiveData
import com.mmoreno.favmovies.app.FavMoviesApplication
import com.mmoreno.favmovies.model.concurrency.ioThread

/**
 * Custom class following the repository pattern
 * for interacting with the Movie Table
 * @author [Mario]
 */
class MovieRepository : Repository<Movie, Int> {


    private val movieDao = FavMoviesApplication.database.movieDao()

    /**
     * Custom method for adding a new movie record
     * @param [entity] instance of [Movie] to be saved
     * @return [Unit]
     */
    override fun add(entity: Movie) {
        ioThread {
            movieDao.insert(entity)
        }
    }

    /**
     * Custom method for deleting a movie record
     * @author [entity] instance of [Movie] to be deleted
     * @return [Unit]
     */
    override fun delete(entity: Movie) {
        ioThread {
            movieDao.delete(entity)
        }
    }

    /**
     * Method for updating a movie record
     * @param [entity] instance of [Movie] to be updated
     * @return [Unit]
     */
    override fun update(entity: Movie) {
        ioThread {
            movieDao.update(entity)
        }
    }

    /**
     * Method for retrieving a single record
     * @param [id] identifies the unique id of a Movie record
     * @return [LiveData]
     */
    override fun findById(id: Int): LiveData<Movie> {
        return movieDao.findById(id)
    }

    /**
     * Method for retrieving all the Movie records saved in the Movie
     * table
     * @return [LiveData]
     */
    override fun getAll(): LiveData<MutableList<Movie>> = movieDao.getMovies()
}