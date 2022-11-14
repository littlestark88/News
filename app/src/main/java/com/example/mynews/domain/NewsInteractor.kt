package com.example.mynews.domain

import androidx.paging.PagingData
import com.example.mynews.domain.model.response.everything.ArticlesEverythingList
import com.example.mynews.domain.model.response.source.SourceList
import com.example.mynews.domain.model.response.topheadlines.ArticlesTopHeadlinesList
import kotlinx.coroutines.flow.Flow

class NewsInteractor(private val newsRepository: INewsRepository): INewsUseCase {
    override fun getEverything(search: String) =
        newsRepository.getEverything(search)

    override fun getTopHeadlines(country: String) =
        newsRepository.getTopHeadlines(country)

    override fun getSource(category: String) =
        newsRepository.getSource(category)
}