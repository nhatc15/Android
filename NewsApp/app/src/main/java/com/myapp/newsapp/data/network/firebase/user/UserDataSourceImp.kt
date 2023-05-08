package com.myapp.newsapp.data.network.firebase.user

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.myapp.newsapp.data.network.firebase.model.FireBaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class UserDataSourceImp(
    private val firebaseAuth: FirebaseAuth,
    private val databaseReference: DatabaseReference
    ): UserDataSource {
    override suspend fun signIn(email: String, password: String): Boolean {
        return try{
            firebaseAuth.signInWithEmailAndPassword(email,password).await()
            true
        }
        catch (e: FirebaseException){
            false
        }
    }

    override suspend fun signUp(fireBaseUser: FireBaseUser): Boolean {
        return try{
            firebaseAuth.createUserWithEmailAndPassword(fireBaseUser.email,fireBaseUser.password).await()
            val currentUser = firebaseAuth.currentUser
            currentUser?.let {
                databaseReference.child("User").child(it.uid).setValue(fireBaseUser.apply { uid = it.uid })
            }
            true
        }
        catch (e: FirebaseException){
            false
        }
    }

    override suspend fun signOut(fireBaseUser: FireBaseUser) {
        firebaseAuth.signOut()
    }

    override fun updateUserInfo(fireBaseUser: FireBaseUser?, updatedInfo: HashMap<String, Any?>): Boolean {
        return try {
            if (fireBaseUser != null) {
                fireBaseUser.uid = firebaseAuth.currentUser!!.uid
                val userRef = databaseReference.child("User").child(fireBaseUser.uid!!)
                userRef.updateChildren(updatedInfo)
                true
            } else {
                false
            }
        } catch (ex: FirebaseException) {
            false
        }
    }
    override fun getUserInfo(): Flow<FireBaseUser> {
        val userRef = databaseReference.child("User").child(firebaseAuth.currentUser!!.uid)
        return callbackFlow {
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(FireBaseUser::class.java)
                    user?.let {
                        trySend(it)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            }

            userRef.addValueEventListener(valueEventListener)
            awaitClose {userRef.removeEventListener(valueEventListener)}
        }
    }

    override fun getUserUid(): String {
        return firebaseAuth.currentUser!!.uid
    }
}