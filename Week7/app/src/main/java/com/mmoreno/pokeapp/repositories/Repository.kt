package com.mmoreno.pokeapp.repositories

class Repository{
    val databaseRepository:DatabaseRepository
    init {
        databaseRepository = DatabaseRepository()
    }
}