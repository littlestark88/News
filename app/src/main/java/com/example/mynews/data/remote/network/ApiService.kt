package com.example.mynews.data.remote.network

import com.example.mynews.BuildConfig.API_KEY
import com.example.mynews.data.remote.response.everything.EverythingResponse
import com.example.mynews.data.remote.response.source.SourceResponse
import com.example.mynews.data.remote.response.topheadlines.TopHeadlinesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("everything")
    suspend fun getEverything(
        @Query("q")search: String? = null,
        @Query("pageSize") pageSize: Int? = 0,
        @Query("page") page: Int? = 0,
        @Query("apiKey")apiKey: String? = API_KEY
    ): EverythingResponse

    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("country")country: String? = null,
        @Query("pageSize") pageSize: Int? = 0,
        @Query("page") page: Int? = 0,
        @Query("apiKey")apiKey: String? = API_KEY
    ): TopHeadlinesResponse

    @GET("top-headlines/sources")
    fun getSourceByCategory(
        @Query("category")category: String? = null,
        @Query("pageSize") pageSize: Int? = 0,
        @Query("page") page: Int? = 0,
        @Query("apiKey")apiKey: String? = API_KEY
    ): SourceResponse
}