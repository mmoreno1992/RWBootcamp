package com.mmoreno.pokeapp.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mmoreno.pokeapp.model.dao.PokeDao

/**
 * Implementation of the database clase for interacting with SQLite using Room
 */
@Database(entities = [PokeEntity::class], version = 1, exportSchema = false)
abstract class PokeDatabase : RoomDatabase() {

    abstract fun pokeDao(): PokeDao

}