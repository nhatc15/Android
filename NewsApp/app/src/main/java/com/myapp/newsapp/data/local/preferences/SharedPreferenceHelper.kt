package com.myapp.newsapp.data.local.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import com.myapp.newsapp.util.Constants.Companion.EMAIL
import com.myapp.newsapp.util.Constants.Companion.IS_GET
import com.myapp.newsapp.util.Constants.Companion.IS_LOGGED_IN
import com.myapp.newsapp.util.Constants.Companion.IS_OPEN_FIRST_TIME
import com.myapp.newsapp.util.Constants.Companion.PASSWORD
import javax.inject.Inject

class SharedPreferenceHelper @Inject constructor(
    private val sharedPreferences: SharedPreferences
){
    fun setEmail(email: String){
        sharedPreferences.edit{
            putString(EMAIL, email)
            apply()
        }
    }

    fun getEmail() = sharedPreferences.getString(EMAIL, "")

    fun setPassword(password: String){
        sharedPreferences.edit{
            putString(PASSWORD, password)
            apply()
        }
    }

    fun getPassword() = sharedPreferences.getString(PASSWORD,"")

    fun setIsLoggedFirstTime(isLoggedFirstTime: Boolean){
        sharedPreferences.edit {
            putBoolean(IS_OPEN_FIRST_TIME, isLoggedFirstTime)
            apply()
        }
    }

    fun getISLoggedFirstTime() = sharedPreferences.getBoolean(IS_OPEN_FIRST_TIME,true)

    fun setIsLoggedIn(isLoggedIn: Boolean){
        sharedPreferences.edit {
            putBoolean(IS_LOGGED_IN, isLoggedIn)
            apply()
        }
    }

    fun getIsLoggedIn() = sharedPreferences.getBoolean(IS_LOGGED_IN, false)

    fun setIsGetNewByCategory(isGet: Boolean){
        sharedPreferences.edit{
            putBoolean(IS_GET, isGet)
            apply()
        }
    }

    fun getISGetNewsByCategory() = sharedPreferences.getBoolean(IS_GET, true)
}