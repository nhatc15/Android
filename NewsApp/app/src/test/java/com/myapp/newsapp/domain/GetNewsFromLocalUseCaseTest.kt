package com.myapp.newsapp.domain

import com.myapp.newsapp.domain.repo.NewsRepository
import com.myapp.newsapp.domain.usecase.news.GetNewsFromLocalUseCase
import com.myapp.newsapp.presentation.model.Article
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetNewsFromLocalUseCaseTest {
    private val newsRepository = mockk<NewsRepository>()
    private val getNewsFromLocalUseCase = GetNewsFromLocalUseCase(newsRepository)

    @Test
    fun getNewsFromLocalReturnFailed() = runBlocking {
        val page = 1
        val emptyList = emptyList<Article>()
        coEvery { newsRepository.getNewsFromLocal(page) } returns emptyList

        val flow = getNewsFromLocalUseCase(page)

        val expected = listOf(NewsResource.Loading, NewsResource.Failed("Empty Data!"))
        assertEquals(expected,flow.toList())
    }
}