package com.myapp.newsapp.domain.repo

import com.myapp.newsapp.presentation.model.Article

interface NewsRepository {
    //Local
    suspend fun getNewsFromLocal(offset: Int):List<Article>
    suspend fun insertNews(list: List<Article>,category: String): List<Long>
    suspend fun searchByTitle(query: String,page: Int): List<Article>
    suspend fun getNewsByCategoryFromLocal(category: String): List<Article>
    //Network
    suspend fun searchForNews(searchQuery: String, page: Int):List<Article>
    suspend fun getLatestNews(page: Int) : List<Article>
    suspend fun getNewsByCategory(category: String, page: Int): List<Article>
//    suspend fun getNewsBySource(source: String, page: Int): List<Article>

//

}