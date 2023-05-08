package com.myapp.newsapp.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myapp.newsapp.data.local.db.entities.ArticleEntity

@Dao
interface ArticleDao {
    @Insert
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("SELECT * FROM Article WHERE category = '' ORDER BY publishedAt DESC LIMIT 20 OFFSET :offset")
    suspend fun getAllArticles(offset: Int): List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticles(listArticles: List<ArticleEntity>): List<Long>

    @Query("SELECT * FROM Article WHERE title LIKE '%' || :query || '%' LIMIT 5 OFFSET :offset")
    suspend fun searchByTitle(query: String,offset: Int): List<ArticleEntity>

    @Query("SELECT * FROM Article WHERE category = :category ORDER BY publishedAt DESC")
    suspend fun getArticlesByCategory(category: String): List<ArticleEntity>
}