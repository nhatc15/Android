package com.myapp.newsapp.presentation.ui.fragment.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.newsapp.domain.NewsResource
import com.myapp.newsapp.domain.usecase.news.GetNewsByCategoryFromLocalUseCase
import com.myapp.newsapp.domain.usecase.news.GetNewsFromLocalUseCase
import com.myapp.newsapp.presentation.model.Article
import com.myapp.newsapp.presentation.state.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsFromLocalUseCase: GetNewsFromLocalUseCase,
    private val getNewsByCategoryFromLocalUseCase: GetNewsByCategoryFromLocalUseCase,
) :ViewModel() {

    private val _getNewsState = MutableLiveData<State>()
    val getNewsState: LiveData<State> = _getNewsState
    var latestNewsPage : Int = 0
    private val latestNewsList= mutableListOf<Article>()

    private val _getNewsByCategoryState = MutableLiveData<State>()
    val getNewsByCategoryState: LiveData<State> = _getNewsByCategoryState

    init {
        CoroutineScope(Dispatchers.IO).launch {
            getNews()
        }
    }

    fun getNews(){
        viewModelScope.launch {
            getNewsFromLocalUseCase(latestNewsPage).collect() { result->
                when(result){
                    is NewsResource.Loading -> _getNewsState.value = State(isLoading = true)
                    is NewsResource.Failed -> _getNewsState.value = State(isSuccess = false, isFailed = true, message = result.message)
                    is NewsResource.Success -> {
                        val oldList = latestNewsList
                        val newsList = result.data
                        oldList.addAll(newsList)
                        _getNewsState.value = State(isSuccess = true, isFailed = false, data = latestNewsList)
                    }
                }
            }
        }
    }

    fun getNewsByCategory(category: String){
        viewModelScope.launch {
            getNewsByCategoryFromLocalUseCase(category).collect(){ result->
                Log.d("tag","Get news by category")
                when(result) {
                    is NewsResource.Loading -> _getNewsByCategoryState.value = State(isLoading = true)
                    is NewsResource.Failed -> _getNewsByCategoryState.value = State(isSuccess = false, isFailed = true, message = result.message)
                    is NewsResource.Success -> _getNewsByCategoryState.value = State(isSuccess = true, isFailed = false, data = result.data)
                }
            }
        }
    }

}