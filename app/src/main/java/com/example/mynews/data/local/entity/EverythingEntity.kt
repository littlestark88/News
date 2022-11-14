package com.example.mynews.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "EverythingTable")
data class EverythingEntity(

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Int? = 0,

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
