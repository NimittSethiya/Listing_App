package com.example.listingapp.`interface`

import com.example.listingapp.constant.Config
import com.example.listingapp.model.Users
import com.example.listingapp.model.WeatherData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherNetworkService {

    @GET("weather?appid=f215722f41c90d48c0c99dad9b5bd379")
    suspend fun getWeatherReport(@Query(value = "lat") lat:String, @Query(value = "lon") lng:String) : Response<WeatherData>
    companion object {
        var networkService: WeatherNetworkService? = null
        fun getInstance() : WeatherNetworkService {
            if (networkService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Config.WEATHER_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                networkService = retrofit.create(WeatherNetworkService::class.java)
            }
            return networkService!!
        }
    }

}