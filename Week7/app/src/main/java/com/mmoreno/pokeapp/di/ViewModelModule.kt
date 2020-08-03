package com.mmoreno.pokeapp.di

import com.mmoreno.pokeapp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Custom Module for injecting viewModel
 * using Koin :D
 */
val mainViewModule = module {
    viewModel {
        //Getting the MainViewModel and also injecting the repository in its constructor
        MainViewModel(get())
    }
}