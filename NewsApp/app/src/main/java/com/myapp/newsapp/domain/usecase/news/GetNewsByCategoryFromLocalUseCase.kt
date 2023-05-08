package com.myapp.newsapp.domain.usecase.news

import com.myapp.newsapp.domain.NewsResource
import com.myapp.newsapp.domain.repo.NewsRepository
import com.myapp.newsapp.presentation.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetNewsByCategoryFromLocalUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(category:String): Flow<NewsResource<List<Article>>> = flow{
        emit(NewsResource.Loading)
        val list = newsRepository.getNewsByCategoryFromLocal(category)
        val result = if(list.isEmpty()){
            NewsResource.Failed("Empty Data!")
        }
        else{
            NewsResource.Success(list)
        }
        emit(result)
    }.flowOn(Dispatchers.IO)
}