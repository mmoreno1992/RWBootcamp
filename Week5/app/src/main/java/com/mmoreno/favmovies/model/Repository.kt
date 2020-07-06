package com.mmoreno.favmovies.model

import androidx.lifecycle.LiveData

/**
 * Base interface for extending in all the repositories we create
 * [T] represents the type of instance to insert in the database
 * [L] represents the data type of the primary key in the table
 * this is useful in the [findById] method
 */
interface Repository<T, L> {
    /**
     * Method for saving to a database
     * [entity] instance to be saved
     */
    fun add(entity: T)

    /**
     * Method for updating a record in the database
     * @param [entity] instance to be updated
     */
    fun update(entity: T)

    /**
     * Method for deleting a record in the database
     * @param [entity] instance to be deleted
     */
    fun delete(entity: T)

    /**
     * Method for finding an instance based on its ID (Primary Key)
     * @param [id] Unique Identifier in the table
     * @return [LiveData]
     */
    fun findById(id: L): LiveData<T>

    /**
     * Method for retrieving all the instances in the table
     * @return [LiveData]
     */
    fun getAll(): LiveData<MutableList<T>>
}