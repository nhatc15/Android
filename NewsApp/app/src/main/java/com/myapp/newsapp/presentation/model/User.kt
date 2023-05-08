package com.myapp.newsapp.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uid: String? = "",
    val email: String = "",
    val password: String = "",
    val firstName: String? = "",
    val lastName: String? = "",
    val birth: String? = ""
): Parcelable
