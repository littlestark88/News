package com.example.mynews.data.remote.remotemediator.source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.mynews.data.local.NewsDatabase
import com.example.mynews.data.local.entity.SourceEntity
import com.example.mynews.data.local.entity.SourceRemoteKeysEntity
import com.example.mynews.data.remote.network.ApiService
import com.example.mynews.utils.Const
import com.example.mynews.utils.DataMapper

@OptIn(ExperimentalPagingApi::class)
class SourceRemoteMediator(
    private val database: NewsDatabase,
    private val apiService: ApiService,
    private val category: String
): RemoteMediator<Int, SourceEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SourceEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getSourceRemoteKeysClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: Const.INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getSourceRemoteKeysForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getSourceRemoteKeysForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        return try {
            val responseData = apiService.getSourceByCategory(category, state.config.pageSize, page)
            val endOfPaginationReached = responseData.sourcesList?.isEmpty()
            database.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    database.remoteKeysSourceDao().deleteSourceRemoteKeys()
                    database.sourceDao().deleteAllSource()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached == true) null else page + 1
                val keys = responseData.sourcesList?.map {
                    SourceRemoteKeysEntity(id = it.id.toString(), prevKey =prevKey, nextKey =nextKey)
                }
                database.remoteKeysSourceDao().insertSourceRemoteKeys(keys)
                Log.e("sourceRemoteMediator", "load: ${responseData.sourcesList?.size}", )
                database.sourceDao().insertAllSource(DataMapper.mapGetSourceEntity(responseData.sourcesList))
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached == true)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getSourceRemoteKeysForLastItem(state: PagingState<Int, SourceEntity>): SourceRemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            database.remoteKeysSourceDao().getSourceRemoteKeysId(data.id)
        }
    }

    private suspend fun getSourceRemoteKeysForFirstItem(state: PagingState<Int, SourceEntity>): SourceRemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            database.remoteKeysSourceDao().getSourceRemoteKeysId(data.id)
        }
    }

    private suspend fun getSourceRemoteKeysClosestToCurrentPosition(state: PagingState<Int, SourceEntity>): SourceRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysSourceDao().getSourceRemoteKeysId(id)
            }
        }
    }
}