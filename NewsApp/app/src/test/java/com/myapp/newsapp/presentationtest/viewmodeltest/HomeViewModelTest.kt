package com.myapp.newsapp.presentationtest.viewmodeltest

import com.myapp.newsapp.domain.NewsResource
import com.myapp.newsapp.domain.usecase.news.GetNewsByCategoryFromLocalUseCase
import com.myapp.newsapp.domain.usecase.news.GetNewsFromLocalUseCase
import com.myapp.newsapp.presentation.model.Article
import com.myapp.newsapp.presentation.state.State
import com.myapp.newsapp.presentation.ui.fragment.home.HomeViewModel
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import junit.framework.Assert
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @InjectMockKs
    private lateinit var viewModel: HomeViewModel

    @MockK
    lateinit var getNewsFromLocalUseCase: GetNewsFromLocalUseCase

    @MockK
    lateinit var getNewsByCategoryFromLocalUseCase: GetNewsByCategoryFromLocalUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        getNewsByCategoryFromLocalUseCase = mockk()
        getNewsByCategoryFromLocalUseCase = mockk()
        viewModel = HomeViewModel(getNewsFromLocalUseCase, getNewsByCategoryFromLocalUseCase)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    // Test cases go here
    @Test
    fun `getNews should update state when use case returns loading`() = testDispatcher.runBlockingTest {
        // Given
        val newsList = listOf(Article(title = "Title 1"), Article(title = "Title 2"))
        val page = 1
        coEvery { getNewsFromLocalUseCase(1) }coAnswers {
            flowOf(NewsResource.Success(newsList))
        }

        viewModel.getNews()

        advanceTimeBy(1000)

        viewModel.getNewsState.runCatching {
            TestCase.assertEquals(viewModel.getNewsState.value, State(isSuccess = true, isFailed = false, isLoading = false, data = newsList))
        }

    }
}