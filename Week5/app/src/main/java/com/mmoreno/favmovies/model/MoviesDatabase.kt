package com.mmoreno.favmovies.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mmoreno.favmovies.model.converters.BooleanConverter
import com.mmoreno.favmovies.model.converters.DateConverter

/**
 * Custom Class for Creating the SQLite Database
 */
@Database(version = 1, entities = [Movie::class])
@TypeConverters(DateConverter::class, BooleanConverter::class)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}