package com.mmoreno.favmovies.model.concurrency

import java.util.concurrent.Executors

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

/**
 * Utility method to run blocks on a dedicated background thread
 * We use a single executor for allowing only one operation at the same
 * in the database
 */
fun ioThread(f: () -> Unit) {
    IO_EXECUTOR.execute(f)
}