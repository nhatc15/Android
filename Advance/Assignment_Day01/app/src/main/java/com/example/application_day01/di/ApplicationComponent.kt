package com.example.application_day01.di

import com.example.application_day01.data.provider.UserProvider
import com.example.application_day01.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(userProvider: UserProvider)
}