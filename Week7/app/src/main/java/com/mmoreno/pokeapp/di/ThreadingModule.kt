package com.mmoreno.pokeapp.di

import com.mmoreno.pokeapp.util.PagingRequestHelper
import org.koin.dsl.module
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * @author Custom class for providing some threading
 * instances with Dependency Injection
 */
val threadingModule = module {
    single {
        Executors.newSingleThreadExecutor()
    }

    single {
        PagingRequestHelper(get<ExecutorService>())
    }
}