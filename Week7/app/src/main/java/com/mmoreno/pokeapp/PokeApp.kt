package com.mmoreno.pokeapp

import android.app.Application
import com.mmoreno.pokeapp.di.databaseModule
import com.mmoreno.pokeapp.di.mainViewModule
import com.mmoreno.pokeapp.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class PokeApp : Application() {
    companion object {
        lateinit var instance: PokeApp
        const val workName = "DownloadData"
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
       // database = Room.databaseBuilder(this, PokeDatabase::class.java, ).build()
        startKoin {
            androidContext(this@PokeApp)
            androidLogger()
            modules(listOf(databaseModule, mainViewModule, networkModule))
        }
    }


}