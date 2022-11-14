package com.example.mynews.data.remote.response.topheadlines

import com.google.gson.annotations.SerializedName

data class ArticlesTopHeadlinesListItem(

    @SerializedName("author")
    val author: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("urlToImage")
    val urlToImage: String?,

    @SerializedName("publishedAt")
    val publishedApi: String?,

    @SerializedName("content")
    val content: String?
)
