package com.myapp.newsapp.domain.usecase.news

import com.myapp.newsapp.domain.NewsResource
import com.myapp.newsapp.domain.repo.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InsertCategoryNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(category:String,page: Int): Flow<NewsResource<List<Long>>> = flow{
        emit(NewsResource.Loading)
        try {
            val long = newsRepository.insertNews(newsRepository.getNewsByCategory(category,page),category)
            if(long.isEmpty()){
                emit(NewsResource.Failed("Can't insert"))
            }
            else{
                emit(NewsResource.Success(long))
            }
        }catch (ex:Exception){
            emit(NewsResource.Failed(ex.message.toString()))
        }

    }.flowOn(Dispatchers.IO)
}