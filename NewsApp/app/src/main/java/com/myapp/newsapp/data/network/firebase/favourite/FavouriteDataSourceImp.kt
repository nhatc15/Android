package com.myapp.newsapp.data.network.firebase.favourite

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.myapp.newsapp.data.network.firebase.model.FirebaseFavouriteNew
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FavouriteDataSourceImp @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val databaseReference: DatabaseReference
): FavouriteDataSource {
    override suspend fun insertFavouriteNews(new: FirebaseFavouriteNew): Boolean {
        return try{
            val favouriteNews =databaseReference.child("Favourite").child(firebaseAuth.currentUser!!.uid).push()
            new.uid = favouriteNews.key.toString()
            favouriteNews.setValue(new).await()
            true
        }catch (ex: FirebaseException){
            false
        }
    }

    override fun getFavouriteNews(): Flow<List<FirebaseFavouriteNew>> {
        val FavouriteNewsList = arrayListOf<FirebaseFavouriteNew>()
        val FavouriteNewsRef = firebaseAuth.currentUser!!.let {
            databaseReference.child("Favourite").child(
                it.uid)
        }
        return callbackFlow {
            val valueEventListener = object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    FavouriteNewsList.clear()
                    for(news in snapshot.children){
                        news.getValue(FirebaseFavouriteNew::class.java)?.let {
                            FavouriteNewsList.add(it)
                        }
                        trySend(FavouriteNewsList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }

            FavouriteNewsRef.addValueEventListener(valueEventListener)
            awaitClose { FavouriteNewsRef.removeEventListener(valueEventListener) }
        }
    }

    override suspend fun deleteFavouriteNews(new: FirebaseFavouriteNew): Boolean {
        return try{
            val favouriteNews =databaseReference.child("Favourite").child(firebaseAuth.currentUser!!.uid).child(new.uid)
            favouriteNews.removeValue().await()
            true
        }catch (ex: FirebaseException){
            false
        }
    }
}