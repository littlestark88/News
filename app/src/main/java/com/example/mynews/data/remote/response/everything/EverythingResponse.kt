package com.example.mynews.data.remote.response.everything

import com.google.gson.annotations.SerializedName

data class EverythingResponse(
    @SerializedName("articles")
    var articlesEverythingList: List<ArticlesEverythingListItem>? = null
)
