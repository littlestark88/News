package com.example.mynews.di

import com.example.mynews.domain.INewsUseCase
import com.example.mynews.domain.NewsInteractor
import com.example.mynews.presentasion.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<INewsUseCase> { NewsInteractor(get()) }
}

val viewModelModule = module {
    viewModel { NewsViewModel(get()) }
}