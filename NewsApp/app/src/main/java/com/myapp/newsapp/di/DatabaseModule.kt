package com.myapp.newsapp.di

import android.content.Context
import androidx.room.Room
import com.myapp.newsapp.data.local.db.ArticleDao
import com.myapp.newsapp.data.local.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext applicationContext: Context): ArticleDatabase {
        return Room
            .databaseBuilder(
                applicationContext,
                ArticleDatabase::class.java,
            "news_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(database: ArticleDatabase): ArticleDao {
        return database.getArticleDao()
    }
}