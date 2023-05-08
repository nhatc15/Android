package com.myapp.newsapp.domain.usecase.user

import com.myapp.newsapp.domain.UserResource
import com.myapp.newsapp.domain.repo.UserRepository
import com.myapp.newsapp.presentation.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddFavouriteNewsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(article: Article): Flow<UserResource<String>> = flow {
        emit(UserResource.Loading)
        val result = userRepository.addToFavourite(article)
        emit(result)
    }.flowOn(Dispatchers.IO)
}