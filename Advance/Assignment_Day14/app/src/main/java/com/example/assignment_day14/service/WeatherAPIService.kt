package com.example.assignment_day14.service

import com.example.assignment_day14.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


//https://api.openweathermap.org/data/2.5/weather?q=HaNoi&appid=ec29bc5d8537d560e34359d17700ef53
class WeatherAPIService {

    private val BASE_URL = "https://api.openweathermap.org/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    fun getDataService(cityName: String): Single<WeatherModel>{
        return api.getData(cityName)
    }
}