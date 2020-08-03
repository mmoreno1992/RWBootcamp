package com.mmoreno.pokeapp.di

import com.mmoreno.pokeapp.networking.PokeService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Custom Module for networking operations
 * designed for using with Dependency Injection with Koin
 */
val networkModule = module {

    //Getting the json converter
    fun getGsonConverter() = GsonConverterFactory.create()

    //Getting the OkHttpClient for retrofit instance
    fun buildClient(): OkHttpClient =
        //Commented the HttpLogginInterceptor I only used it in testing
        OkHttpClient.Builder()/*
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })*/
            .build()

    //Getting the retrofit instance
    fun getRetrofitInstance(
        urlService: String, httpClient: OkHttpClient, gsonConverter: GsonConverterFactory
    ) = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(urlService)
        .addConverterFactory(gsonConverter)
        .build()


    //Singleton for injecting the okhttp client
    single { buildClient() }

    //getting the Api Service
    single { getRetrofitInstance(get(), get(), get()).create(PokeService::class.java) }

    //Get the gson converter
    single { getGsonConverter() }

    //Geting the URL also using dependency injection
    single {
        PokeService.BASE_URL
    }

}

