package com.example.mynews.data.remote.response.everything

import com.google.gson.annotations.SerializedName

data class ArticlesEverythingListItem(

    @SerializedName("source")
    val source: EverythingSourceItem?,

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
