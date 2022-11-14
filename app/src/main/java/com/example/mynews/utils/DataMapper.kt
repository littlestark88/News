package com.example.mynews.utils

import androidx.paging.PagingData
import androidx.paging.map
import com.example.mynews.data.local.entity.EverythingEntity
import com.example.mynews.data.local.entity.SourceEntity
import com.example.mynews.data.local.entity.TopHeadlinesEntity
import com.example.mynews.data.remote.response.everything.ArticlesEverythingListItem
import com.example.mynews.data.remote.response.source.SourceListItem
import com.example.mynews.data.remote.response.topheadlines.ArticlesTopHeadlinesListItem
import com.example.mynews.domain.model.response.everything.ArticlesEverythingList
import com.example.mynews.domain.model.response.source.SourceList
import com.example.mynews.domain.model.response.topheadlines.ArticlesTopHeadlinesList

object DataMapper {

    fun mapGetEverythingEntity(listData: List<ArticlesEverythingListItem>?): List<EverythingEntity> =
        listData?.map {
            EverythingEntity(
                id = it.source?.id.orEmpty(),
                author = it.author.orEmpty(),
                title = it.title.orEmpty(),
                description = it.description.orEmpty(),
                url = it.url.orEmpty(),
                urlToImage = it.urlToImage.orEmpty(),
                publishedApi = it.publishedApi.orEmpty(),
                content = it.content.orEmpty()
            )
        } ?: emptyList()

    fun mapGetEverythingPaging(listData: PagingData<EverythingEntity>): PagingData<ArticlesEverythingList> =
        listData.map {
            ArticlesEverythingList(
                id = it.id,
                author = it.author.orEmpty(),
                title = it.title.orEmpty(),
                description = it.description.orEmpty(),
                url = it.url.orEmpty(),
                urlToImage = it.urlToImage.orEmpty(),
                publishedApi = it.publishedApi.orEmpty(),
                content = it.content.orEmpty()
            )
        }

    fun mapGetSourceEntity(listData: List<SourceListItem>?): List<SourceEntity> =
        listData?.map {
            SourceEntity(
                id = it.id.orEmpty(),
                name = it.name.orEmpty(),
                description = it.description.orEmpty(),
                url = it.url.orEmpty(),
                category = it.category.orEmpty(),
                language = it.language.orEmpty(),
                country = it.country.orEmpty()
            )
        } ?: emptyList()

    fun mapGetSourcePaging(listData: PagingData<SourceEntity>): PagingData<SourceList> =
        listData.map {
            SourceList(
                id = it.id,
                name = it.name.orEmpty(),
                description = it.description.orEmpty(),
                url = it.url.orEmpty(),
                category = it.category.orEmpty(),
                language = it.language.orEmpty(),
                country = it.country.orEmpty()
            )
        }

    fun mapGetTopHeadlinesEntity(listData: List<ArticlesTopHeadlinesListItem>?): List<TopHeadlinesEntity> =
        listData?.map {
            TopHeadlinesEntity(
                id = it.source?.id.orEmpty(),
                author = it.author.orEmpty(),
                title = it.title.orEmpty(),
                description = it.description.orEmpty(),
                url = it.url.orEmpty(),
                urlToImage = it.urlToImage.orEmpty(),
                publishedApi = it.publishedApi.orEmpty(),
                content = it.content.orEmpty()
            )
        } ?: emptyList()

    fun mapGetTopHeadlinesPaging(listData: PagingData<TopHeadlinesEntity>): PagingData<ArticlesTopHeadlinesList> =
        listData.map {
            ArticlesTopHeadlinesList(
                id = it.id,
                author = it.author.orEmpty(),
                title = it.title.orEmpty(),
                description = it.description.orEmpty(),
                url = it.url.orEmpty(),
                urlToImage = it.urlToImage.orEmpty(),
                publishedApi = it.publishedApi.orEmpty(),
                content = it.content.orEmpty()
            )
        }
}