package com.example.listingapp.model

import com.example.listingapp.`interface`.NetworkService
import com.example.listingapp.`interface`.WeatherNetworkService

class WeatherRepository constructor(private val networkService: WeatherNetworkService) {

    suspend fun getWeatherReport(lat:String,lng:String) = networkService.getWeatherReport(lat, lng)

}