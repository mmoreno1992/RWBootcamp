package com.mmoreno.pokeapp.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Builds retrofit dependencies.
 */
fun buildClient(): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()


fun buildRetrofit(): Retrofit {

    return Retrofit.Builder()
        .client(buildClient())
        .baseUrl(PokeService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun buildApiService(): PokeService =
    buildRetrofit().create(PokeService::class.java)