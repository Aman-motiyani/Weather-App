package com.example.weatherapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.utils.ApiInterface
import com.example.weatherapp.utils.ApiUtilities
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.repository.WeatherViewModelFactory
import com.example.weatherapp.viewModel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var weatherViewModel: WeatherViewModel

    @RequiresApi(Build.VERSION_CODES.N)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lat=intent.getStringExtra("lat")
        val lon=intent.getStringExtra("long")

        val apiInterface = ApiUtilities.getInstance().create(ApiInterface::class.java)
        val weatherRepository = WeatherRepository(apiInterface)

        weatherViewModel = ViewModelProvider(this, WeatherViewModelFactory(weatherRepository,lat,lon))[WeatherViewModel::class.java]
        weatherViewModel.refreshData(lat , lon)

        cLocation.setOnClickListener{
            weatherViewModel.refreshData(lat,lon)
            Toast.makeText(this, "Current Location Attributes",Toast.LENGTH_SHORT).show()
        }


        search_bar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchData(searchImage)
            }
            false
        }

        getLiveData()
    }


    @SuppressLint("SetTextI18n")
    private fun getLiveData(){
        weatherViewModel.weather.observe(this) { data ->
            data?.let {
                temp.text = data.main.temp.toString() + "°C"
                status.text = data.weather[0].description.capitalize(Locale.getDefault())
                temp_min.text = "Min. Temp : " + data.main.temp_min.toString() + "°C"
                temp_max.text = "Max. Temp : " + data.main.temp_max.toString() + "°C"
                sunrise.text = SimpleDateFormat("hh:mm a" , Locale.ENGLISH).format((data.sys.sunrise*1000))
                sunsetTime.text = SimpleDateFormat("hh:mm a" , Locale.ENGLISH).format((data.sys.sunset*1000))
                windSpeed.text = data.wind.speed.toString() + " Km/h"
                pressureText.text = data.main.pressure.toString() + " hPa"
                humidityText.text = data.main.humidity.toString() + "%"
                VisibilityText.text = data.visibility.toString() + " m"

            }

        }
    }


    fun searchData(view:View) {
        val cName = search_bar.editableText.toString()
        weatherViewModel.getCityData(cName)
        closeKeyboard()
        Toast.makeText(this, cName.toUpperCase(),Toast.LENGTH_SHORT).show()
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }



}
