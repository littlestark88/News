package com.example.mynews.domain.model.response.topheadlines

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticlesTopHeadlinesList(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedApi: String,
    val content: String
): Parcelable
