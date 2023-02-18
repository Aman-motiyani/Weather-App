package com.example.weatherapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.WeatherData
import com.example.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(private val WeatherRepository : WeatherRepository) : ViewModel() {


    fun refreshData(lat: String?, lon: String?){
        viewModelScope.launch(Dispatchers.IO){
            getLiveData(lat,lon)
        }
    }

    fun getCityData(cname : String){
        viewModelScope.launch(Dispatchers.IO){
            WeatherRepository.getCityData(cname)
        }
    }

    private fun getLiveData(lat: String?, lon: String?){
        viewModelScope.launch(Dispatchers.IO){
            WeatherRepository.getCurrentData(lat,lon)
        }
    }

    val weather : LiveData<WeatherData>
        get() = WeatherRepository.wData
}