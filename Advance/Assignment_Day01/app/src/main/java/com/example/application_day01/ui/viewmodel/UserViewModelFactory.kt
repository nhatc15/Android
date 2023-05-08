package com.example.application_day01.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.application_day01.data.repo.UserRepositoryImp
import com.example.application_day01.domain.LoginUsecase
import com.example.application_day01.domain.RegisterUsecase
import javax.inject.Inject

class UserViewModelFactory @Inject constructor(
    private val loginUsecase: LoginUsecase,
    private val registerUsecase: RegisterUsecase,
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(loginUsecase,registerUsecase) as T
    }
}