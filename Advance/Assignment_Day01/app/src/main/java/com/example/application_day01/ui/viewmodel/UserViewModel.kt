package com.example.application_day01.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application_day01.data.UserState
import com.example.application_day01.data.model.User
import com.example.application_day01.domain.LoginUsecase
import com.example.application_day01.domain.RegisterUsecase
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val loginUsecase: LoginUsecase,
    private val registerUsecase: RegisterUsecase,
):ViewModel() {

    private val _login = MutableLiveData<UserState?>()
    val loginState: LiveData<UserState?> get() = _login

    private val _register = MutableLiveData<UserState?>()
    val registerState: LiveData<UserState?> get() = _register


    fun login(username: String, password: String) {
        viewModelScope.launch {
            _login.value = loginUsecase(username, password)
        }
    }
    fun register(email: String, username: String, password: String) {
        viewModelScope.launch {
            _register.value = registerUsecase(User(email, username, password))
        }
    }
}