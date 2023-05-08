package com.myapp.newsapp.util

class Constants {
    companion object{
        const val SIGN_UP_SUCCESSFUL ="Signup successfully"
        const val SPLASH_SCREEN_TIME_DELAY = 3500L
        const val EMAIL: String = "EMAIL"
        const val PASSWORD: String = "PASSWORD"
        const val IS_OPEN_FIRST_TIME: String = "IS_OPEN_FIRST_TIME"
        const val IS_LOGGED_IN: String = "IS_LOGGED_IN"
        const val IS_GET : String = "IS_GET"
        const val SHARED_PREFERENCES_NAME: String = "NEWSAPP"
//        const val API_KEY = "0f6f533f70ae44448074dd7d68d912a8"
        const val API_KEY = "b1d7e4ffedb245b6a0c7c60c1f295acc"
//        const val API_KEY = "4638053bb42f4d36ba33bcfee0b6a8a7"
        const val BASE_URL = "https://newsapi.org"
        const val SEARCH_NEWS_TIME_DELAY = 500L
        const val QUERY_PAGE_SIZE = 20
        const val EMAIL_INVALID = "Invalid email format. Please enter a valid email address."
        const val PASSWORD_INVALID = "Invalid password format. Password needs to be at least 8 characters and 1 uppercase character"
        const val ENTER_PASSWORD_INVALID = "Passwords do not match. Please try again."
        const val LOGGED_IN_REMIND = "Log in to your Kaar Account!"
        const val LOG_IN = "Log in"
        const val LOG_OUT ="Log out"
        const val LOADING = "Loading"
        const val LOG_IN_TO_ADD_TO_FAVOURITE ="Sign in to add to favourite"
        const val ADD_SUCCESSFUL = "Add to favourite successful"
        const val DELETE_FAVOURITE_SUCCESSFULLY = "Successfully deleted article"
        const val SEARCH_REMIND = "Find it on Kaar..."
    }

}