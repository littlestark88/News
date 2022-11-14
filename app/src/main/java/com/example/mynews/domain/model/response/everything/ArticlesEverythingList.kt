package com.example.mynews.domain.model.response.everything

data class ArticlesEverythingList(
    val id: String,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedApi: String,
    val content: String
)
