package com.myapp.newsapp.presentationtest.viewmodeltest

import android.os.Looper
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.myapp.newsapp.domain.UserResource
import com.myapp.newsapp.domain.usecase.user.AddFavouriteNewsUseCase
import com.myapp.newsapp.presentation.model.Article
import com.myapp.newsapp.presentation.ui.fragment.article.ArticleViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("DEPRECATION")
@ExperimentalCoroutinesApi
class ArticleViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var insertFavouriteNewsUseCase: AddFavouriteNewsUseCase

    @InjectMockKs
    private lateinit var vm: ArticleViewModel

    @Before
    fun setUp(){
        mockkStatic(Looper::class)
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        insertFavouriteNewsUseCase = mockk()
        vm = ArticleViewModel(insertFavouriteNewsUseCase)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun insertFavouriteNewsGetSuccess(): Unit = testDispatcher.run {
        val article = Article("", "a","b")

        every { Looper.getMainLooper() } returns mockk()
        coEvery { insertFavouriteNewsUseCase(article) } coAnswers {
            flowOf(UserResource.Success("Add successfully"))
        }

        vm.insertFavouriteNews(article)

//        vm.favouriteState.runCatching {
            Assert.assertEquals(vm.favouriteState.value, UserResource.Success("Add successfully"))
//        }
    }
}