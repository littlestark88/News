package com.example.mynews.base

import android.app.Application
import com.example.mynews.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NewsAppApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@NewsAppApplication)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                    newsRemoteMediatorModule
                )
            )
        }
    }
}