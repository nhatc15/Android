package com.myapp.newsapp.data.network.firebase.favourite

import com.myapp.newsapp.data.network.firebase.model.FirebaseFavouriteNew
import kotlinx.coroutines.flow.Flow

interface FavouriteDataSource {
    suspend fun insertFavouriteNews(new: FirebaseFavouriteNew): Boolean
    fun getFavouriteNews(): Flow<List<FirebaseFavouriteNew>>
    suspend fun deleteFavouriteNews(new: FirebaseFavouriteNew): Boolean
}