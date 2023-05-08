package com.myapp.newsapp.data.network.firebase.model

import android.provider.ContactsContract.CommonDataKinds.Email
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class FireBaseUser(
    var uid: String? = "",
    var email: String = "",
    var password: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var birth: String = "",
)
