package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class CurrentWeather @Inject constructor(
    val location: Location,
    val current: Current,
    val forecast: Forecast
)

data class Location @Inject constructor(
    @SerializedName("name")
    val city: String,
    val country: String,
    @SerializedName("localtime_epoch")
    val time: Int
)

data class Current @Inject constructor(
    @SerializedName("temp_c")
    val temp: Double,
    val condition: Condition,
    @SerializedName("wind_kph")
    val wind: Double,
    @SerializedName("humidity")
    val hum: Int
)

data class Condition @Inject constructor(
    val text: String,
    val icon: String
)

data class Forecast @Inject constructor(
    val forecastday: List<Forecastday>
)

data class Forecastday @Inject constructor(
    @SerializedName("date_epoch")
    val time: Int,
    val hour: List<Hour>,
    val day: Day
)

data class Day @Inject constructor(
    @SerializedName("avgtemp_c")
    val temp: Double,
    val condition: DCondition
)

data class DCondition @Inject constructor(
    val text: String,
    val icon: String
)

data class Hour @Inject constructor(
    val condition: FCondition,
    @SerializedName("temp_c")
    val temp: Double,
    @SerializedName("time_epoch")
    val time: Int
)

data class FCondition @Inject constructor(
    val text: String,
    val icon: String
)




