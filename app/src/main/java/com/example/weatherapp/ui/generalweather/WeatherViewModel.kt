package com.example.weatherapp.ui.generalweather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Constants
import com.example.weatherapp.data.model.CurrentWeather
import com.example.weatherapp.data.response.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _state = MutableLiveData<CurrentWeather?>()
    val state: LiveData<CurrentWeather?> get() = _state

    suspend fun getWeather(city: String) {
        try {
            val result = apiService.getCurrentWeather(Constants.API_KEY, city)
            if (result.isSuccessful) {
                result.body()?.let {
                    _state.postValue(it)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _state.postValue(null)
        }
    }
}