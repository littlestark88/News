package com.example.mynews.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "TopHeadlinesRemoteKeysTable")
data class TopHeadlinesRemoteKeysEntity(
    @PrimaryKey
    @SerializedName("id")
    val id: String,

    @SerializedName("prevKey")
    val prevKey: Int?,

    @SerializedName("nextKey")
    val nextKey: Int?
)
