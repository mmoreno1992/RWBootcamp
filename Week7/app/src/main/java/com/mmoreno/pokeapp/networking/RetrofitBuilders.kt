package com.mmoreno.pokeapp.networking

/**
 * Builds retrofit dependencies.
 */

/**
 * Returns an OkHttpClient for using with Retrofit
 */
/*
fun buildClient(): OkHttpClient =
    //Commented the HttpLogginInterceptor I only used it in testing
    OkHttpClient.Builder()
        */
/*.addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })*//*

        .build()
*/

/**
 * Returns an instance of Retrofit
 */
/*fun buildRetrofit(urlService: String): Retrofit {

    return Retrofit.Builder()
        .client(buildClient())
        .baseUrl(PokeService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun buildApiService(): PokeService =
    buildRetrofit(PokeService.BASE_URL).create(PokeService::class.java)*/