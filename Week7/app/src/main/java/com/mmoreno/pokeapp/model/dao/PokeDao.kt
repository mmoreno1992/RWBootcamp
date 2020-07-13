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
    @Query("SELECT name, url FROM POKE_TABLE")
    fun pokeRecords(): DataSource.Factory<Int, PokeEntity>
}