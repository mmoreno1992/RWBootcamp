package com.mmoreno.pokeapp.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mmoreno.pokeapp.model.dao.PokeDao
import com.mmoreno.pokeapp.networking.PokeService
import com.mmoreno.pokeapp.util.sendDataSyncNotification
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Custom Coroutine Worker for downloading automatically some data automatically
 * @author Mario
 * In this class I need to implement KoinComponent for accessing
 * methods like get(), inject() of Koin
 */
class DownloadDataWorker(
    context: Context,
    parameters: WorkerParameters
) :
    CoroutineWorker(context, parameters), KoinComponent {

    private val pokeApi: PokeService by inject()
    private val pokeDao: PokeDao by inject()

    override suspend fun doWork(): Result {
        return try {
            val howManyPokemonsAreStored = pokeDao.countPokeRecords()
            val request = pokeApi.listPokemons(offset = howManyPokemonsAreStored)
            val records = request.results
            if (records.isNotEmpty()) {
                pokeDao.insert(records)
            }
            //Log.i("POKEAPP", "DEBERIA ENVIAR NOTIFICACION")
            sendDataSyncNotification(applicationContext)
            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }

    }
}