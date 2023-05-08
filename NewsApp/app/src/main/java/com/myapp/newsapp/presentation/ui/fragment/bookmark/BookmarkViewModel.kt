package com.myapp.newsapp.presentation.ui.fragment.bookmark

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.newsapp.domain.UserResource
import com.myapp.newsapp.domain.usecase.user.DeleteFavouriteNewsUseCase
import com.myapp.newsapp.domain.usecase.user.GetFavouriteNewsUseCase
import com.myapp.newsapp.presentation.model.Article
import com.myapp.newsapp.presentation.state.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getFavouriteNewsUseCase: GetFavouriteNewsUseCase,
    private val deleteFavouriteNewsUseCase: DeleteFavouriteNewsUseCase

):ViewModel() {

    val _getFavouriteNewsState = MutableLiveData<State>()
    val getFavouriteNewsState: LiveData<State> = _getFavouriteNewsState

    val _deleteFavouriteState = MutableLiveData<State>()
    val deleteFavouriteState: LiveData<State> = _deleteFavouriteState

    fun getFavouriteNews(){
        viewModelScope.launch {
            getFavouriteNewsUseCase().collect(){ result ->
                when(result){
                    is UserResource.Loading -> _getFavouriteNewsState.value = State(isLoading = true)
                    is UserResource.Success -> _getFavouriteNewsState.value = State(isSuccess = true, data = result.data)
                    is UserResource.Failed -> _getFavouriteNewsState.value = State(isFailed = true, message = result.toString())
                }
            }
        }
    }

    fun deleteFavouriteNews(article: Article){
        viewModelScope.launch {
            deleteFavouriteNewsUseCase(article).collect(){ result->
                Log.d("tag","Delete Favourite news")
                when(result){
                    is UserResource.Loading -> _deleteFavouriteState.value = State(isLoading = true)
                    is UserResource.Success -> _deleteFavouriteState.value = State(isSuccess = true)
                    is UserResource.Failed -> _deleteFavouriteState.value = State(isFailed = true, message = result.toString())
                }
            }
        }
    }
}