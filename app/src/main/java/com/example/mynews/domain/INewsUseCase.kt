package com.example.mynews.domain

import androidx.paging.PagingData
import com.example.mynews.domain.model.response.everything.ArticlesEverythingList
import com.example.mynews.domain.model.response.source.SourceList
import com.example.mynews.domain.model.response.topheadlines.ArticlesTopHeadlinesList
import kotlinx.coroutines.flow.Flow

interface INewsUseCase {
    fun getEverything(search: String): Flow<PagingData<ArticlesEverythingList>>
    fun getTopHeadlines(country: String): Flow<PagingData<ArticlesTopHeadlinesList>>
    fun getSource(category: String): Flow<PagingData<SourceList>>
}