package com.myapp.newsapp.presentation.ui.fragment.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.newsapp.domain.UserResource
import com.myapp.newsapp.domain.usecase.user.SignUpUseCase
import com.myapp.newsapp.presentation.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase) : ViewModel() {

    private val _signUpState = MutableLiveData<UserResource<String>?>()
    val signUpState: LiveData<UserResource<String>?> = _signUpState

    fun signUp(user: User) {
        viewModelScope.launch {
            signUpUseCase(user).collect(){
                _signUpState.value = it
            }

        }
    }
}