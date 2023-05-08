package com.example.assignment_day14.service

import com.example.assignment_day14.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.openweathermap.org/data/2.5/weather?q=HaNoi&appid=ec29bc5d8537d560e34359d17700ef53

interface WeatherAPI {

    @GET("data/2.5/weather?&units=metric&appid=ec29bc5d8537d560e34359d17700ef53")
    fun getData(
        @Query("q") cityName: String
    ):Single<WeatherModel>
}