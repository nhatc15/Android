package com.myapp.newsapp.data.network

import com.myapp.newsapp.data.model.NewsResponse
import com.myapp.newsapp.data.network.newsapi.NewsApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class NewsApiServiceTest {
    private lateinit var newsApiService: NewsApiService
    private val response = mockk<Response<NewsResponse>>()
    private val newsResponse = mockk<NewsResponse>()

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        newsApiService = mockk()
    }

    @Test
    fun `searchForNews returns success`() = runBlocking {

        every { response.isSuccessful } returns true
        every { response.body() } returns newsResponse
        coEvery { newsApiService.searchForNews(any(), any(), any(), any()) } returns response

        val result = newsApiService.searchForNews()

        assertTrue(result.isSuccessful)
        assertEquals(newsResponse, result.body())
    }

    @Test
    fun `searchForNews returns failure`() = runBlocking {

        every { response.isSuccessful } returns false
        coEvery { newsApiService.searchForNews(any(), any(), any(), any()) } returns response

        val result = newsApiService.searchForNews()

        assertFalse(result.isSuccessful)
    }

    @Test
    fun `getLatestNews return success`() = runBlocking {
        every { response.isSuccessful } returns true
        every { response.body() } returns newsResponse
        coEvery { newsApiService.getLatestNews(any(),any(),any(),any()) } returns response

        val result = newsApiService.getLatestNews()

        assertTrue(result.isSuccessful)
        assertEquals(newsResponse, result.body())
    }

    @Test
    fun `getLatestNews returns failure`() = runBlocking {

        every { response.isSuccessful } returns false
        coEvery { newsApiService.getLatestNews(any(), any(), any(), any()) } returns response

        val result = newsApiService.getLatestNews()

        assertFalse(result.isSuccessful)
    }

    @Test
    fun `getNewsByCategory return success`() = runBlocking {
        every { response.isSuccessful } returns true
        every { response.body() } returns newsResponse
        coEvery { newsApiService.getNewsByCategory(any(),any(),any(),any()) } returns response

        val result = newsApiService.getNewsByCategory()

        assertTrue(result.isSuccessful)
        assertEquals(newsResponse, result.body())
    }

    @Test
    fun `getNewsByCategory returns failure`() = runBlocking {

        every { response.isSuccessful } returns false
        coEvery { newsApiService.getNewsByCategory(any(), any(), any(), any()) } returns response

        val result = newsApiService.getNewsByCategory()

        assertFalse(result.isSuccessful)
    }
}