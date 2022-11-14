package com.example.mynews.data.remote.response.topheadlines

import com.google.gson.annotations.SerializedName

data class TopHeadlinesResponse(
    @SerializedName("articles")
    var articlesTopHeadlinesList: List<ArticlesTopHeadlinesListItem>? = null
)
