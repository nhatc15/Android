package com.myapp.newsapp.data.network.firebase.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class FirebaseFavouriteNew(
    var uid: String ="",
    val author: String = "",
    val content: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val source: String = "",
    val title: String ="",
    val url: String = "",
    val urlToImage: String= "",
)