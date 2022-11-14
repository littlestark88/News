package com.example.mynews.domain.model.response.topheadlines

data class ArticlesTopHeadlinesList(
    val id: String,
    val sourceItem: TopHeadlinesSource? = null,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedApi: String,
    val content: String
)
