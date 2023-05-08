package com.example.application_day01.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.example.application_day01.data.repo.UserRepository
import com.example.application_day01.data.repo.UserRepositoryImp
import com.example.application_day01.di.MyApplication
import javax.inject.Inject

class UserProvider: ContentProvider() {

    @Inject
    lateinit var userRepositoryImp: UserRepositoryImp
    override fun onCreate(): Boolean {
        (context as MyApplication).appComponent.inject(this)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return userRepositoryImp.getAllUserCursor().apply {
            setNotificationUri(context?.contentResolver,uri)
        }
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

}