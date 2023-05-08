package com.myapp.newsapp.data.network.newsapi

import com.myapp.newsapp.data.model.NewsResponse
import com.myapp.newsapp.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apikey")
        apikey: String = Constants.API_KEY,
        @Query("pageSize")
        pageSize: Int = 10
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun getLatestNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apikey")
        apikey: String = Constants.API_KEY,
        @Query("pageSize")
        pageSize: Int = 20
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun getNewsByCategory(
        @Query("category")
        category: String = "business",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apikey")
        apikey: String = Constants.API_KEY,
        @Query("pageSize")
        pageSize: Int = 10
    ): Response<NewsResponse>

//    @GET("v2/top_headlines")
//    suspend fun getNewsBySource(
//        @Query("sources")
//        source: String = "bbc-news",
//        @Query("page")
//        pageNumber: Int = 1,
//        @Query("apikey")
//        apikey: String = Constants.API_KEY
//    ): Response<NewsResponse>
}
