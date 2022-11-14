package com.example.mynews.presentasion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mynews.domain.INewsUseCase
import com.example.mynews.domain.model.response.everything.ArticlesEverythingList
import com.example.mynews.domain.model.response.source.SourceList
import com.example.mynews.domain.model.response.topheadlines.ArticlesTopHeadlinesList

class NewsViewModel(
    private val newsUseCase: INewsUseCase
): ViewModel() {

    fun getEverything(search: String): LiveData<PagingData<ArticlesEverythingList>> =
        newsUseCase.getEverything(search).cachedIn(viewModelScope).asLiveData()

    fun getTopHeadlines(country: String): LiveData<PagingData<ArticlesTopHeadlinesList>> =
        newsUseCase.getTopHeadlines(country).cachedIn(viewModelScope).asLiveData()

    fun getSource(category: String): LiveData<PagingData<SourceList>> =
        newsUseCase.getSource(category).cachedIn(viewModelScope).asLiveData()
}