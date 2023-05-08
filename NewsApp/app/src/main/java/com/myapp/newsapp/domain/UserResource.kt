package com.myapp.newsapp.domain

sealed class UserResource<out T :Any> {
    data class Success<out T : Any>(val data: T) : UserResource<T>()
    data class Failed(val message: String) : UserResource<Nothing>()
    object Loading : UserResource<Nothing>()
}
