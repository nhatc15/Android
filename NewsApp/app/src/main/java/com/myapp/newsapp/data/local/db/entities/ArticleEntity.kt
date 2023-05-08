package com.myapp.newsapp.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Article")
data class ArticleEntity(
    val author: String = "",
    val content: String = "",
    val description: String = "",
    val publishedAt: String = "",
    val source: String = "",
    @PrimaryKey
    val title: String = "",
    val url: String = "",
    val urlToImage: String = "",
    @ColumnInfo("category")
    val category: String = "",
)