package com.mmoreno.pokeapp.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mmoreno.pokeapp.PokeApp
import com.mmoreno.pokeapp.networking.buildApiService
import com.mmoreno.pokeapp.util.sendDataSyncNotification

class DownloadDataWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    private val pokeApi by lazy { buildApiService() }

    override suspend fun doWork(): Result {
        try {
            val pokeDao = PokeApp.database.pokeDao()
            val howManyPokemonsAreStored = pokeDao.countPokeRecords()

            val request = pokeApi.listPokemons(offset = howManyPokemonsAreStored)
            val records = request.results
            if (records.isNotEmpty()) {
                pokeDao.insert(records)
            }
            Log.i("POKEAPP", "DEBERIA ENVIAR NOTIFICACION")

            sendDataSyncNotification(applicationContext)

            return Result.success()
        } catch (ex: Exception) {
            return Result.failure()
        }

    }
}