package com.myapp.newsapp.domain.usecase.user

import com.myapp.newsapp.domain.repo.UserRepository
import com.myapp.newsapp.presentation.model.User
import javax.inject.Inject

class SignOutUseCase@Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User){
        userRepository.signOut(user)
    }
}