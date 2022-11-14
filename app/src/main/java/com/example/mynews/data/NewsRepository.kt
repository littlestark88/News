package com.example.mynews.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mynews.data.local.NewsDatabase
import com.example.mynews.data.remote.network.ApiService
import com.example.mynews.data.remote.remotemediator.everything.EverythingRemoteMediator
import com.example.mynews.data.remote.remotemediator.source.SourceRemoteMediator
import com.example.mynews.data.remote.remotemediator.topheadlines.TopHeadlinesRemoteMediator
import com.example.mynews.domain.INewsRepository
import com.example.mynews.domain.model.response.everything.ArticlesEverythingList
import com.example.mynews.domain.model.response.everything.Everything
import com.example.mynews.domain.model.response.source.Source
import com.example.mynews.domain.model.response.source.SourceList
import com.example.mynews.domain.model.response.topheadlines.ArticlesTopHeadlinesList
import com.example.mynews.domain.model.response.topheadlines.TopHeadlines
import com.example.mynews.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepository(
    private val apiService: ApiService,
    private val newsDatabase: NewsDatabase
): INewsRepository {

    override fun getEverything(search: String): Flow<PagingData<ArticlesEverythingList>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = EverythingRemoteMediator(newsDatabase, apiService, search),
            pagingSourceFactory = {
                newsDatabase.getAllEverythingDao().getAllEverything()
            }
        ).flow.map {
            DataMapper.mapGetEverythingPaging(it)
        }
        Log.e("newsRemoteMediaotir", "load: Start" )
    }

    override fun getTopHeadlines(country: String): Flow<PagingData<ArticlesTopHeadlinesList>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = TopHeadlinesRemoteMediator(newsDatabase, apiService, country),
            pagingSourceFactory = {
                newsDatabase.getAllTopHeadlinesDao().getAllTopHeadlines()
            }
        ).flow.map {
            DataMapper.mapGetTopHeadlinesPaging(it)
        }
    }

    override fun getSource(category: String): Flow<PagingData<SourceList>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = SourceRemoteMediator(newsDatabase, apiService, category),
            pagingSourceFactory = {
                newsDatabase.getAllSourceDao().getAllSource()
            }
        ).flow.map {
            DataMapper.mapGetSourcePaging(it)
        }
    }
}