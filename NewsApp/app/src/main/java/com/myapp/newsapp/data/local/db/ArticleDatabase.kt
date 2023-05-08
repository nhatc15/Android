package com.myapp.newsapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myapp.newsapp.data.local.db.entities.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 3,
    exportSchema = false
)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
}