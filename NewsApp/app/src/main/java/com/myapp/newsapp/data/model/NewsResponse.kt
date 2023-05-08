package com.myapp.newsapp.data.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("articles")
    @Expose
    val articles: List<ArticleModel?>?,

    @SerializedName("status")
    @Expose
    val status: String?,

    @SerializedName("totalResults")
    @Expose
    val totalResults: Int?
)