package com.myapp.newsapp.presentationtest.viewmodeltest

import android.content.Context
import com.myapp.newsapp.domain.NewsResource
import com.myapp.newsapp.domain.usecase.news.InsertCategoryNewsUseCase
import com.myapp.newsapp.domain.usecase.news.InsertLastestNewsUseCase
import com.myapp.newsapp.presentation.state.State
import com.myapp.newsapp.presentation.ui.fragment.splash.SplashViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScheduler
import org.junit.Before
import org.junit.Test

class SplashViewModelTest {
    private val insertNewsUseCase = mockk<InsertLastestNewsUseCase>()
    private val insertCategoryNewsUseCase = mockk<InsertCategoryNewsUseCase>()
    private lateinit var viewModel: SplashViewModel

    private val testDispatcher = TestCoroutineScheduler()

    @Before
    fun setup() {
        viewModel = SplashViewModel(insertNewsUseCase, insertCategoryNewsUseCase)
    }

}