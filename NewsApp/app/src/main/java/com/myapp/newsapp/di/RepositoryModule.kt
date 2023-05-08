package com.myapp.newsapp.di

import com.myapp.newsapp.data.local.db.ArticleDao
import com.myapp.newsapp.data.network.firebase.favourite.FavouriteDataSource
import com.myapp.newsapp.data.network.firebase.user.UserDataSource
import com.myapp.newsapp.data.network.newsapi.NewsApiService
import com.myapp.newsapp.data.repo.NewsRepositoryImp
import com.myapp.newsapp.data.repo.UserRepositoryImp
import com.myapp.newsapp.domain.repo.NewsRepository
import com.myapp.newsapp.domain.repo.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(userDataSource: UserDataSource,favouriteDataSource: FavouriteDataSource): UserRepository {
        return UserRepositoryImp(userDataSource,favouriteDataSource)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsDao: ArticleDao,
        apiService: NewsApiService
    ): NewsRepository {
        return NewsRepositoryImp(newsDao, apiService)
    }
}