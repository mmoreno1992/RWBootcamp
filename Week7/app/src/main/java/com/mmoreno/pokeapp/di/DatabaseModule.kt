package com.mmoreno.pokeapp.di

import androidx.room.Room
import com.mmoreno.pokeapp.model.PokeDatabase
import com.mmoreno.pokeapp.model.PokeRepository
import org.koin.dsl.module

/**
 * Custom database module for injecting instances of a database
 * dao, and a repository using Koin
 */
val databaseModule = module {

    //Getting the database instancen(Singleton)
    single {
        Room
            .databaseBuilder(get(), PokeDatabase::class.java, "pokeDB.db")
            .build()
    }

    //Getting the dao instance (Singleton)
    single { get<PokeDatabase>().pokeDao() }

    //Getting the PokeRepository (Singleton)
    single { PokeRepository(get()) }

}