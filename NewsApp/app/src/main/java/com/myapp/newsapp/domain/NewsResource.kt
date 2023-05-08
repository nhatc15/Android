package com.myapp.newsapp.domain

sealed class NewsResource<out T :Any> {
    data class Success<out T : Any>(val data: T) : NewsResource<T>()
    data class Failed(val message: String) : NewsResource<Nothing>()
    object Loading : NewsResource<Nothing>()
}

