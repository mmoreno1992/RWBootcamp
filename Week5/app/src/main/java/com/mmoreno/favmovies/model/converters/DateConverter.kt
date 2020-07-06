package com.mmoreno.favmovies.model.converters

import androidx.room.TypeConverter
import java.util.*

/**
 * Custom converter for storing/retrieving
 * a Date attribute in the entity as a Long number in
 * SQLite.
 * @author Mario
 */
class DateConverter {
    /**
     * Converter method from a [Long] instance to a [Date]
     * @param [dateInLong] [Long] attribute to convert
     * @return [Date]
     */
    @TypeConverter
    fun fromLongToDate(dateInLong: Long) = Date(dateInLong)

    /**
     * Converter method from a [Date] instance to a [Long]
     * @param [date] [Date] attribute to convert
     * @return [Long]
     */
    @TypeConverter
    fun fromDateToLong(date: Date) = date.time
}
