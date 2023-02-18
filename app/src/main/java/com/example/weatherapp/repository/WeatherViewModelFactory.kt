package com.example.weatherapp.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import com.example.weatherapp.viewModel.WeatherViewModel

class WeatherViewModelFactory(private val weatherRepository: WeatherRepository, private val lat: String?, private val lon: String?):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(weatherRepository) as T
    }
}