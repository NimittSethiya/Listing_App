package com.example.listingapp.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.listingapp.model.*
import kotlinx.coroutines.*
import retrofit2.await

class WeatherViewModel constructor(private val weatherRepository: WeatherRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val weatherData = MutableLiveData<WeatherData>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.localizedMessage?.let { Log.e("ERROR   ", it.toString()) }
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getWeatherReport(lat:String,lng:String) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = weatherRepository.getWeatherReport(lat,lng)
//            Log.e("ERROR",response.toString())
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.e("ERROR",response.toString())
                    weatherData.postValue(response.body())
                    loading.value = false
                } else {
                    Log.e("ERROR",response.toString())
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}