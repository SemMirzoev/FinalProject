package com.example.weatherapp.data.response


import com.example.weatherapp.data.model.CurrentWeather


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v1/current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String?,
        @Query("q") city: String?
    ): Response<CurrentWeather>


    @GET("v1/forecast.json")
    suspend fun getForecastHourWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("days") days: Int
    ): Response<CurrentWeather>

    @GET("v1/forecast.json")
    suspend fun getForecastDayWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("days") days: Int
    ): Response<CurrentWeather>
}