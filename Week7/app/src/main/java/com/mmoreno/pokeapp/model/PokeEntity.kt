package com.mmoreno.pokeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class representing the Poke Response
 * and also an entity in the database
 */
@Entity(tableName = "POKE_TABLE")
data class PokeEntity(
    @PrimaryKey
    val name: String,
    val url: String
){
    fun getIdFromUrl():String{
        val sub = url.substring()
    }
}