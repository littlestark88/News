package com.example.mynews.data.remote.remotemediator.topheadlines

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mynews.data.local.NewsDatabase
import com.example.mynews.data.local.entity.TopHeadlinesEntity
import com.example.mynews.data.local.entity.TopHeadlinesRemoteKeysEntity
import com.example.mynews.data.remote.network.ApiService
import com.example.mynews.utils.Const
import com.example.mynews.utils.DataMapper

@OptIn(ExperimentalPagingApi::class)
class TopHeadlinesRemoteMediator(
    private val database: NewsDatabase,
    private val apiService: ApiService,
    private val country: String
): RemoteMediator<Int, TopHeadlinesEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopHeadlinesEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getTopHeadlinesRemoteKeysClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: Const.INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getTopHeadlinesRemoteKeysForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getTopHeadlinesRemoteKeysForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        return try {
            val responseData = apiService.getTopHeadlines(country, state.config.pageSize, page)
            val endOfPaginationReached = responseData.articlesTopHeadlinesList?.isEmpty()
            database.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    database.remoteKeysTopHeadlinesDao().deleteTopHeadlinesRemoteKeys()
                    database.getAllTopHeadlinesDao().deleteAllTopHeadlines()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached == true) null else page + 1
                val keys = responseData.articlesTopHeadlinesList?.map {
                    TopHeadlinesRemoteKeysEntity("1",prevKey =prevKey, nextKey =nextKey)
                }
                database.remoteKeysTopHeadlinesDao().insertTopHeadlinesRemoteKeys(keys)
                database.getAllTopHeadlinesDao().insertAllTopHeadlines(DataMapper.mapGetTopHeadlinesEntity(responseData.articlesTopHeadlinesList))
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached == true)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getTopHeadlinesRemoteKeysForLastItem(state: PagingState<Int, TopHeadlinesEntity>): TopHeadlinesRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysTopHeadlinesDao().getTopHeadlinesRemoteKeysId(data.id.toString())
        }
    }

    private suspend fun getTopHeadlinesRemoteKeysForFirstItem(state: PagingState<Int, TopHeadlinesEntity>): TopHeadlinesRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.remoteKeysTopHeadlinesDao().getTopHeadlinesRemoteKeysId(data.id.toString())
        }
    }

    private suspend fun getTopHeadlinesRemoteKeysClosestToCurrentPosition(state: PagingState<Int, TopHeadlinesEntity>): TopHeadlinesRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysTopHeadlinesDao().getTopHeadlinesRemoteKeysId(id.toString())
            }
        }
    }
}