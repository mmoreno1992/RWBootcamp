package com.mmoreno.favmovies.model

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

/**
 * Using DAO's inheritance capability and making use of
 * Generics [T]
 */
interface BaseDao<T> {

    /**
     * @param entity varargs param entites for storing in the
     * database
     */
    @Insert
    fun insert(vararg entity: T)

    /**
     * Custom method for deleting a record from the database
     * @param entity instance to be deleted from the database
     */
    @Delete
    fun delete(entity: T)

    /**
     * Custom method for updating a record in the database
     * @param entity
     */
    @Update
    fun update(entity: T)
}