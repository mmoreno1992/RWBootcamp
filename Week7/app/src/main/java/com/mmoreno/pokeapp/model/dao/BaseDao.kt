package com.mmoreno.pokeapp.model.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy

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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg entity: T)

}