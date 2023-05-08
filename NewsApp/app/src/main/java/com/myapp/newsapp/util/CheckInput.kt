package com.myapp.newsapp.util

object CheckInput {
    fun isEmailValid(email: String): Boolean {
        val pattern = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return pattern.matches(email)
    }
    fun isPasswordValid(password: String): Boolean{
        return (password.length >= 6 && password.any{it.isUpperCase()})
    }
}