package com.myapp.newsapp.presentation.ui.fragment.explore

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.newsapp.domain.NewsResource
import com.myapp.newsapp.domain.usecase.news.SearchLocalNewsUseCase
import com.myapp.newsapp.domain.usecase.news.SearchingNewsUseCase
import com.myapp.newsapp.presentation.model.Article
import com.myapp.newsapp.presentation.state.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val searchingNewsUseCase: SearchingNewsUseCase,
    private val searchLocalNewsUseCase: SearchLocalNewsUseCase
): ViewModel() {

    private val _searchNewsState = MutableLiveData<State>()
    val searchNewsState: LiveData<State> = _searchNewsState
    var searchNewsPage : Int = 1
    private val searchNewsList: MutableList<Article>? = mutableListOf<Article>()

    var newQuery: String? = ""
    var oldQuery: String? = ""

    fun searchLocalNews(searchQuery: String){
        viewModelScope.launch {
            newQuery = searchQuery
            searchLocalNewsUseCase(searchQuery,searchNewsPage).collect(){result->
                when(result){
                    is NewsResource.Loading -> _searchNewsState.value = State(isLoading = true)
                    is NewsResource.Failed -> _searchNewsState.value = State(isSuccess = false, isFailed = true, message = result.message)
                    is NewsResource.Success -> {
                        if(searchNewsList == null || newQuery != oldQuery){
                            searchNewsPage++
                            oldQuery = newQuery
                            searchNewsList?.clear()
                            searchNewsList?.addAll(result.data)
                        }
                        else{
                            searchNewsPage++
                            val oldList = searchNewsList
                            val newsList = result.data
                            oldList.addAll(newsList)
                        }
                        _searchNewsState.value = State(isSuccess = true, isFailed = false, data = searchNewsList)
                    }
                }
            }
        }
    }
    @SuppressLint("SuspiciousIndentation")
    fun searchNews(searchQuery: String){
        viewModelScope.launch {
            newQuery = searchQuery
                searchingNewsUseCase(searchQuery,searchNewsPage).collect(){result->
                    when(result){
                        is NewsResource.Loading -> _searchNewsState.value = State(isLoading = true)
                        is NewsResource.Failed -> _searchNewsState.value = State(isSuccess = false, isFailed = true, message = result.message)
                        is NewsResource.Success -> {
                            if(searchNewsList == null || newQuery != oldQuery){
                                searchNewsPage++
                                oldQuery = newQuery
                                searchNewsList?.clear()
                                searchNewsList?.addAll(result.data)
                            }
                            else{
                                searchNewsPage++
                                val oldList = searchNewsList
                                val newsList = result.data
                                oldList.addAll(newsList)
                            }
                            _searchNewsState.value = State(isSuccess = true, isFailed = false, data = searchNewsList)
                        }
                    }
                }

        }
    }
}