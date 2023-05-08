package com.example.application_day01.data

sealed class UserState{
    object Success:UserState()
    object Failed:UserState()
}