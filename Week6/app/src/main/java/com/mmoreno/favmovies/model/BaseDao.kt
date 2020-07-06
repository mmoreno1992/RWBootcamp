package com.mmoreno.favmovies.model

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

/**
 * Using DAO's inheritance capability and making use of
 * @author Mario
 * Generics [T]
 */
interface BaseDao<T> {

    /**
     * @param entity varargs param entities for storing in the
     * database
     */
    @Insert
    suspend fun insert(vararg entity: T)

    /**
     * Custom method for deleting a record from the database
     * @param entity instance to be deleted from the database
     */
    @Delete
    suspend fun delete(entity: T)

    /**
     * Custom method for updating a record in the database
     * @param entity
     */
    @Update
    suspend fun update(entity: T)
}