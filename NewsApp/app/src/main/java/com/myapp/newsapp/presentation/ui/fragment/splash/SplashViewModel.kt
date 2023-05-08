package com.myapp.newsapp.presentation.ui.fragment.splash

import androidx.lifecycle.*
import com.myapp.newsapp.domain.NewsResource
import com.myapp.newsapp.domain.usecase.news.InsertCategoryNewsUseCase
import com.myapp.newsapp.domain.usecase.news.InsertLastestNewsUseCase
import com.myapp.newsapp.presentation.state.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val insertNewsUseCase: InsertLastestNewsUseCase,
    private val insertCategoryNewsUseCase: InsertCategoryNewsUseCase,
): ViewModel(){

    private val _insertNewsState = MutableLiveData<State>()
    val insertNewsState: LiveData<State> = _insertNewsState
    var insertNewsPage: Int = 0

    private val _newsByCategoryState = MutableLiveData<State>()
    val newsByCategoryState: LiveData<State> = _newsByCategoryState
    var newsByCategoryPage: Int = 1
    fun insertLatestNews(){
        viewModelScope.launch {
            insertNewsUseCase(insertNewsPage).collect() { value ->
                insertNewsPage++
                when(value){
                    is NewsResource.Loading -> _insertNewsState.value = State(isLoading = true)
                    is NewsResource.Failed -> _insertNewsState.value = State(isSuccess = false, isFailed = true, message = value.message)
                    is NewsResource.Success -> {
                        insertNewsPage++
                        _insertNewsState.value = State(isSuccess = true, isFailed = false)
                    }
                }
            }
        }
    }
    fun getNewsByCategory(category: String) {
        viewModelScope.launch {
            insertCategoryNewsUseCase(category,newsByCategoryPage).collect(){ value ->
                when(value){
                    is NewsResource.Loading -> _newsByCategoryState.value = State(isLoading = true)
                    is NewsResource.Failed -> _newsByCategoryState.value = State(isSuccess = false, isFailed = true, message = value.message)
                    is NewsResource.Success -> {
                        _newsByCategoryState.value = State(isSuccess = true, isFailed = false)
                    }
                }
            }
        }
    }
}