package com.example.mynews.domain.model.response.source

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SourceList(
    val id: String,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String
): Parcelable
