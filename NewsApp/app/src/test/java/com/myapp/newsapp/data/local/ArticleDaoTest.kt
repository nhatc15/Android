package com.myapp.newsapp.data.local

import com.myapp.newsapp.data.local.db.ArticleDao
import com.myapp.newsapp.data.local.db.entities.ArticleEntity
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ArticleDaoTest {

    private lateinit var articleDao: ArticleDao
    val articles = listOf(
        ArticleEntity("1", "Title 1", "Description 1", "https://example.com/1", "2022-04-22T09:56:00Z", "category"),
        ArticleEntity("2", "Title 2", "Description 2", "https://example.com/2", "2022-04-23T09:56:00Z", "category")
    )
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        articleDao = mockk()
    }

    @Test
    fun testGetAllArticles() = runBlocking {
        val offset = 0
        every { runBlocking { articleDao.getAllArticles(offset) } } returns articles
        val result = articleDao.getAllArticles(offset)
        assertEquals(articles, result)
    }

    @Test
    fun testInsertAllArticle() = runBlocking {
        val articleIds = listOf(1L, 2L)
        every { runBlocking { articleDao.insertAllArticles(articles) } } returns articleIds
        val result = articleDao.insertAllArticles(articles)
        assertEquals(articleIds, result)
    }

    @Test
    fun testSearchByTitle() = runBlocking {
        val query = "Example"
        val offset = 0
        every { runBlocking { articleDao.searchByTitle(query, offset) } } returns articles
        val result = articleDao.searchByTitle(query, offset)
        assertEquals(articles, result)
    }

    @Test
    fun testGetArticlesByCategory() = runBlocking {
        val category = "general"
        every { runBlocking { articleDao.getArticlesByCategory(category) } } returns articles
        val result = articleDao.getArticlesByCategory(category)
        assertEquals(articles,result)
    }
}