package com.myapp.newsapp.di

import com.myapp.newsapp.app.MyApplication
import com.myapp.newsapp.util.CheckInternetConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Provides
    @Singleton
    fun provideApplication(): MyApplication{
        return MyApplication()
    }

    @Provides
    @Singleton
    fun provideCheckInternetConnection(): CheckInternetConnection{
        return CheckInternetConnection()
    }
}