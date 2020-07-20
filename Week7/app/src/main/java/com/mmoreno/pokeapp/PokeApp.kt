package com.mmoreno.pokeapp

import android.app.Application
import androidx.room.Room
import com.mmoreno.pokeapp.model.PokeDatabase
import com.mmoreno.pokeapp.networking.buildApiService

class PokeApp : Application() {
    companion object {
        lateinit var instance: PokeApp
        lateinit var database: PokeDatabase
        val workName = "DownloadData"

        val apiService by lazy {
            buildApiService()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, PokeDatabase::class.java, "pokeDB.db").build()

    }


}