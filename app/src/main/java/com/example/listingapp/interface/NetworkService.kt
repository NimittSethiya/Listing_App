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

interface NetworkService {

    @GET("?results=20")
    suspend fun getRandomUsers(@Query(value = "page") page:Int) : Response<Users>
    companion object {
        var networkService: NetworkService? = null
        fun getInstance() : NetworkService {
            if (networkService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                networkService = retrofit.create(NetworkService::class.java)
            }
            return networkService!!
        }
    }

}