package com.myapp.newsapp.presentation.ui.fragment.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.newsapp.domain.UserResource
import com.myapp.newsapp.domain.usecase.user.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val signInUseCase: SignInUseCase) : ViewModel() {

    private val _signInState = MutableLiveData< UserResource<String>?>()
    val signInState: LiveData<UserResource<String>?> = _signInState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            signInUseCase(email, password).collect(){
                _signInState.value = it
            }
        }
    }
}