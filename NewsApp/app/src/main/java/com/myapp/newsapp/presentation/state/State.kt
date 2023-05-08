package com.myapp.newsapp.presentation.state

import com.myapp.newsapp.presentation.model.Article
import com.myapp.newsapp.presentation.model.User

data class State(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isFailed: Boolean = false,
    val data: List<Article>? = null,
    val message: String? = null,
    val user: User? = null
)