package com.myapp.newsapp.presentation.ui.fragment.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.newsapp.domain.UserResource
import com.myapp.newsapp.domain.usecase.user.AddFavouriteNewsUseCase
import com.myapp.newsapp.domain.usecase.user.DeleteFavouriteNewsUseCase
import com.myapp.newsapp.presentation.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val insertFavouriteNewsUseCase: AddFavouriteNewsUseCase,
) :ViewModel() {
    private val _favouriteState = MutableLiveData<UserResource<String>?>()
    val favouriteState: LiveData<UserResource<String>?> = _favouriteState

    fun insertFavouriteNews(article: Article){
        viewModelScope.launch {
            insertFavouriteNewsUseCase(article).collect(){ result->
                _favouriteState.value = result
            }
        }
    }
}