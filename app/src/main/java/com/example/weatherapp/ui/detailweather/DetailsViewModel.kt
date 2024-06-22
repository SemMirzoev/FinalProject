package com.example.weatherapp.ui.detailweather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.model.Day
import com.example.weatherapp.data.model.Forecastday
import com.example.weatherapp.data.model.Hour
import com.example.weatherapp.data.response.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    val stateHour = MutableLiveData<List<Hour>?>()
    suspend fun getForecast(apiKeys: String, city: String, days: Int) {
        try {
            val result = apiService.getForecastHourWeather(apiKeys, city, days)
            if (result.isSuccessful) {
                stateHour.postValue(result.body()?.forecast?.forecastday?.first()?.hour)
            }
        }catch (e: Exception) {
            e.printStackTrace()
            stateHour.postValue(null)
        }

    }

    val stateDay = MutableLiveData<List<Forecastday>?>()
    suspend fun getDayForecast(apiKeys: String, city: String, days: Int) {
        try {
            val result = apiService.getForecastDayWeather(apiKeys, city, days)
            if (result.isSuccessful) {
                stateDay.postValue(result.body()?.forecast?.forecastday)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            stateDay.postValue(null)
        }
    }
}