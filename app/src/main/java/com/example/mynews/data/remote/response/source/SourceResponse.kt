package com.example.mynews.data.remote.response.source

import com.google.gson.annotations.SerializedName

data class SourceResponse(
    @SerializedName("sources")
    var sourcesList: List<SourceListItem>? = null
)
