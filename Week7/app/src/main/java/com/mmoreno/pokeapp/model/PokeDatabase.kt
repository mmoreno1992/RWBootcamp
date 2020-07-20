package com.mmoreno.pokeapp.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mmoreno.pokeapp.model.dao.PokeDao

/**
 * Implementation of the database clase for interacting with SQLite using Room
 */
@Database(entities = [PokeEntity::class], version = 1, exportSchema = false)
abstract class PokeDatabase : RoomDatabase() {

    abstract fun pokeDao(): PokeDao

    /**
     * A single instance for interacting with the database
     */
    companion object {
        fun create(context: Context): PokeDatabase {
            val databaseBuilder =
                Room.databaseBuilder(context, PokeDatabase::class.java, "pokeDB.db")
            return databaseBuilder.build()
        }
    }
}