package com.example.application_day01.di

import androidx.room.Room
import com.example.application_day01.data.local.UserDatabase
import com.example.application_day01.data.provider.UserProvider
import com.example.application_day01.data.repo.UserRepositoryImp
import com.example.application_day01.domain.GetAllUserUsecase
import com.example.application_day01.domain.LoginUsecase
import com.example.application_day01.domain.RegisterUsecase
import com.example.application_day01.ui.viewmodel.UserViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MyApplication) {
    @Singleton
    @Provides
    fun provideApplicationContext() = application

    @Singleton
    @Provides
    fun provideUserRepositoryImp(userDatabase: UserDatabase): UserRepositoryImp {
        return UserRepositoryImp(userDatabase)
    }

    @Singleton
    @Provides
    fun provideUserDatabase(application: MyApplication): UserDatabase{
        return Room.databaseBuilder(
            application,
            UserDatabase::class.java,
            "userDB"
        ).build()
    }

    @Singleton
    @Provides
    fun provideLoginUsecase(userRepositoryImp: UserRepositoryImp): LoginUsecase{
        return LoginUsecase(userRepositoryImp)
    }

    @Singleton
    @Provides
    fun provideRegisterUsecase(userRepositoryImp: UserRepositoryImp): RegisterUsecase{
        return RegisterUsecase(userRepositoryImp)
    }

    @Singleton
    @Provides
    fun provideGetAllUser(userRepositoryImp: UserRepositoryImp): GetAllUserUsecase{
        return GetAllUserUsecase(userRepositoryImp)
    }

    @Singleton
    @Provides
    fun provideUserViewModelFactory(loginUsecase: LoginUsecase, registerUsecase: RegisterUsecase): UserViewModelFactory{
        return UserViewModelFactory(loginUsecase, registerUsecase)
    }

}