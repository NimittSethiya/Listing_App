package com.example.listingapp.constant

import android.util.Log
import kotlin.math.roundToInt

object Config {
    const val BASE_URL = "https://randomuser.me/api/"
    const val WEATHER_API = "https://api.openweathermap.org/data/2.5/"
}

fun toCelcius(temp: Double?) : String{
    Log.e("temp",temp.toString())
    return ((temp!! - 273.15).roundToInt()).toString()
}