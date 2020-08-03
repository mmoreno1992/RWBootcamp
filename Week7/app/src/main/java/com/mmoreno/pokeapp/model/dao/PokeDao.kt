package com.mmoreno.pokeapp.model.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import com.mmoreno.pokeapp.model.PokeEntity

/**
 * Dao for interacting the the POKE_TABLE
 */
@Dao
interface PokeDao : BaseDao<PokeEntity> {
    /**
     * This DataSource allows us to paginate and at the same time obtain the data
     * wrapped as LiveData for observing in the UI
     */
    @Query("SELECT name, url FROM POKE_TABLE")
    fun pokeRecords(): DataSource.Factory<Int, PokeEntity>

    /**
     * Custom method for counting the number of records in the table
     * for paging purposes
     */
    @Query("SELECT COUNT(1) FROM POKE_TABLE")
    suspend fun countPokeRecords(): Int

}