package com.mmoreno.pokeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class representing the Poke Response
 * and also an entity in the database
 */
@Entity(tableName = "POKE_TABLE")
class PokeEntity(
    @PrimaryKey
    val url: String,
    var name: String
) {
    /**
     * Custom method for getting the ID of a Pokemon
     * this id is given by the API
     */
    fun getIdFromUrl(): String {
        val urlParts = url.split("/")
        return urlParts[urlParts.size - 2]
    }

    /**
     * Overriding default equals method
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PokeEntity

        if (url != other.url) return false
        if (name != other.name) return false

        return true
    }

    /**
     * Overriding default hashCode
     */
    override fun hashCode(): Int {
        var result = url.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

}