package com.example.application_day01.di

import android.app.Application
import com.example.application_day01.di.ApplicationModule
import com.example.application_day01.di.ApplicationComponent
import com.example.application_day01.di.DaggerApplicationComponent

class MyApplication: Application() {
    val appComponent: ApplicationComponent = DaggerApplicationComponent.builder().applicationModule(
        ApplicationModule(this)
    ).build()
}