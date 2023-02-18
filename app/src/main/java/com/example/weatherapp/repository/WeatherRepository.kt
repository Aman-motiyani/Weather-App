package com.example.weatherapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.utils.ApiInterface
import com.example.weatherapp.model.WeatherData

class WeatherRepository(private val apiInterface: ApiInterface) {

    private val weatherLiveData = MutableLiveData<WeatherData>()

    val wData : LiveData<WeatherData>
    get() = weatherLiveData

    suspend fun getCurrentData(lat: String?, lon: String?)
    {
        val result = apiInterface.getCurrentWeatherData(lat,lon)

        if (result.body() != null)
        {
            weatherLiveData.postValue(result.body())
        }
    }

    suspend fun getCityData(cName : String)
    {
        val result = apiInterface.getCityData(cName)

        if (result.body() != null)
        {
            weatherLiveData.postValue(result.body())
        }
    }

}