package com.example.mynews.data.remote.response.source

import com.google.gson.annotations.SerializedName

data class SourceListItem(
    @SerializedName("id")
    val id: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("category")
    val category: String?,

    @SerializedName("language")
    val language: String?,

    @SerializedName("country")
    val country: String?
)
