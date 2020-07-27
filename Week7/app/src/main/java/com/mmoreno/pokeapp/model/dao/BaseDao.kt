package com.mmoreno.pokeapp.model.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy

/**
 * Using DAO's inheritance capability and making use of Generics [T]
 * @author Mario
 *
 */
interface BaseDao<T> {

    /**
     * @param entity varargs param entities for storing in the
     * database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg entity: T)

    /**
     * @param List of entities to be inserted in the
     * database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: List<T>)

}