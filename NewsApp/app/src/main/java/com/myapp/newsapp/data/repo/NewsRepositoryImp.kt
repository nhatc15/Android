package com.myapp.newsapp.data.repo

import com.myapp.newsapp.data.local.db.ArticleDao
import com.myapp.newsapp.data.mapper.NewsMapper
import com.myapp.newsapp.data.model.ArticleModel
import com.myapp.newsapp.data.network.newsapi.NewsApiService
import com.myapp.newsapp.domain.repo.NewsRepository
import com.myapp.newsapp.presentation.model.Article
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class NewsRepositoryImp @Inject constructor(
    private val articleDao: ArticleDao,
    private val newsApiService: NewsApiService,
): NewsRepository {
    //Local
    override suspend fun getNewsFromLocal(offset: Int): List<Article> {
        return NewsMapper.mapFromEntity(articleDao.getAllArticles(offset*20))
    }

    override suspend fun insertNews(list: List<Article>,category: String): List<Long> {
        return articleDao.insertAllArticles(NewsMapper.mapToEntity(list,category))
    }

    override suspend fun searchByTitle(query: String,page: Int): List<Article> {
        return NewsMapper.mapFromEntity(articleDao.searchByTitle(query,page*5))
    }

    override suspend fun getNewsByCategoryFromLocal(category: String): List<Article> {
        return NewsMapper.mapFromEntity(articleDao.getArticlesByCategory(category))
    }

    //Network
    override suspend fun searchForNews(searchQuery: String, page: Int): List<Article> {
        return try {
            val response = newsApiService.searchForNews(searchQuery,page).body()
            NewsMapper.transformTo(response?.articles as List<ArticleModel>)
        }catch (ex:Exception){
            NewsMapper.mapFromEntity(articleDao.searchByTitle(searchQuery,page*10))
        }
    }

    override suspend fun getLatestNews(page: Int): List<Article> {
        val response = newsApiService.getLatestNews("us",page).body()
        return NewsMapper.transformTo(response?.articles as List<ArticleModel>)
    }

    override suspend fun getNewsByCategory(category: String, page: Int): List<Article> {
        val response = newsApiService.getNewsByCategory(category,page).body()
        return NewsMapper.transformTo(response?.articles as List<ArticleModel>)
    }

//    override suspend fun getNewsBySource(source: String, page: Int): List<Article> {
//        val response = newsApiService.getNewsBySource(source,page).body()
//        return NewsMapper.transformTo(response?.articles as List<ArticleModel>)
//    }
}