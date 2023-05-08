package com.myapp.newsapp.presentation.ui.fragment.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.newsapp.domain.UserResource
import com.myapp.newsapp.domain.usecase.user.GetUserInfoUseCase
import com.myapp.newsapp.domain.usecase.user.SignOutUseCase
import com.myapp.newsapp.presentation.model.User
import com.myapp.newsapp.presentation.state.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    ) : ViewModel() {

    private val _getUserInfoState = MutableLiveData<State>()
    val getUserInfoState: LiveData<State> = _getUserInfoState

    fun signOut(user: User) {
        viewModelScope.launch {
            signOutUseCase(user)
        }
    }

    fun getUserInfo(){
        viewModelScope.launch {
            getUserInfoUseCase().collect(){ result->
                when(result){
                    is UserResource.Loading -> _getUserInfoState.value = State(isLoading = true)
                    is UserResource.Failed -> _getUserInfoState.value = State(isFailed = true, isSuccess = false, message = result.message)
                    is UserResource.Success ->{
                        _getUserInfoState.value = State(isSuccess = true, isFailed = false, user = result.data)
                        Log.d("Get User Info",result.data.toString())
                    }
                }
            }
        }
    }

}