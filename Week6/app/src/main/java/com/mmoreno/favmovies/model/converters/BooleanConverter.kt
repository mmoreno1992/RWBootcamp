package com.mmoreno.favmovies.model.converters

import androidx.room.TypeConverter

/**
 * Custom converter for storing/retrieving
 * a boolean attribute as a number
 * SQLite does not have a separate Boolean storage class.
 * Instead, Boolean values are stored as integers 0 (false) and 1 (true).
 * @author Mario
 */
class BooleanConverter {
    /**
     * Converter method from a [Boolean] instance to an [Int]
     * @param [isFavorite] [Boolean] attribute to convert
     * @return [Int]
     */
    @TypeConverter
    fun fromBooleanToNumber(isFavorite: Boolean) = if (isFavorite) 1 else 0

    /**
     * Converter method from a [Int] instance to an [Boolean]
     * @param [numberFavorite] [Int] attribute to convert
     * @return [Boolean]
     */
    @TypeConverter
    fun fromNumberToBoolean(numberFavorite: Int) = numberFavorite == 1
}