package com.example.mynews.di

import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.mynews.BuildConfig.BASE_URL
import com.example.mynews.data.NewsRepository
import com.example.mynews.data.local.NewsDatabase
import com.example.mynews.data.remote.network.ApiService
import com.example.mynews.data.remote.remotemediator.everything.EverythingRemoteMediator
import com.example.mynews.data.remote.remotemediator.source.SourceRemoteMediator
import com.example.mynews.data.remote.remotemediator.topheadlines.TopHeadlinesRemoteMediator
import com.example.mynews.domain.INewsRepository
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single {
        val chuckerCollector = ChuckerCollector(
            context = androidContext(),
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        val chuckerInterceptor = ChuckerInterceptor.Builder(androidContext())
            .collector(chuckerCollector)
            .redactHeaders("Auth-Token", "Bearer")
            .alwaysReadResponseBody(true)
            .build()

        OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val databaseModule = module {
    factory { get<NewsDatabase>().getAllEverythingDao() }
    factory { get<NewsDatabase>().getAllSourceDao() }
    factory { get<NewsDatabase>().getAllTopHeadlinesDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            NewsDatabase::class.java, "News.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val repositoryModule = module {
    factory<INewsRepository> { NewsRepository(get(), get())}
}

val newsRemoteMediatorModule = module {
    factory { EverythingRemoteMediator(get(), get(), get()) }
    factory { SourceRemoteMediator(get(), get(), get()) }
    factory { TopHeadlinesRemoteMediator(get(), get(), get()) }
}