package com.example.mynews.data.remote.remotemediator.everything

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mynews.data.local.NewsDatabase
import com.example.mynews.data.local.entity.EverythingEntity
import com.example.mynews.data.local.entity.EverythingRemoteKeysEntity
import com.example.mynews.data.remote.network.ApiService
import com.example.mynews.utils.Const.INITIAL_PAGE_INDEX
import com.example.mynews.utils.DataMapper

@OptIn(ExperimentalPagingApi::class)
class EverythingRemoteMediator(
    private val database: NewsDatabase,
    private val apiService: ApiService,
    private val search: String
): RemoteMediator<Int, EverythingEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EverythingEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getEverythingRemoteKeysClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getEverythingRemoteKeysForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getEverythingRemoteKeysForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        return try {
            val responseData = apiService.getEverything(search, state.config.pageSize, page)
            val endOfPaginationReached = responseData.articlesEverythingList?.isEmpty()
            database.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    database.remoteKeysEverythingDao().deleteEverythingRemoteKeys()
                    database.everythingDao().deleteAllEverything()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached == true) null else page + 1
                val keys = responseData.articlesEverythingList?.map {
                    EverythingRemoteKeysEntity(id= it.title.toString(),prevKey =prevKey, nextKey =nextKey)
                }
                database.remoteKeysEverythingDao().insertEverythingRemoteKeys(keys)
                database.everythingDao().insertAllEverything(DataMapper.mapGetEverythingEntity(responseData.articlesEverythingList))
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached == true)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getEverythingRemoteKeysForLastItem(state: PagingState<Int, EverythingEntity>): EverythingRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysEverythingDao().getEverythingRemoteKeysId(data.title)
        }
    }

    private suspend fun getEverythingRemoteKeysForFirstItem(state: PagingState<Int, EverythingEntity>): EverythingRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.remoteKeysEverythingDao().getEverythingRemoteKeysId(data.title)
        }
    }

    private suspend fun getEverythingRemoteKeysClosestToCurrentPosition(state: PagingState<Int, EverythingEntity>): EverythingRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.title?.let { title ->
                database.remoteKeysEverythingDao().getEverythingRemoteKeysId(title)
            }
        }
    }

}