package com.mmoreno.pokeapp.model.dao

import androidx.room.*
import com.mmoreno.pokeapp.model.PokeEntity

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

    @Insert
    suspend fun insert(entity: T)

    /**
     * @param List of entities to be inserted in the
     * database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: List<T>)

    /**
     * @param entity varargs param entities to be deleted
     */
    @Delete
    suspend fun delete(vararg entity: T)

    /**
     * @param entity T to be updated
     */
    @Update
    suspend fun update(entity: T)

    /**
     * Selects an entity from the table
     * @param urlId Id of the pokemon
     */
    @Query("SELECT * FROM POKE_TABLE WHERE URL = :urlId")
    suspend fun findById(urlId: String): PokeEntity

}