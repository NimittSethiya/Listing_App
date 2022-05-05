package com.example.listingapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.listingapp.`interface`.WeatherNetworkService
import com.example.listingapp.constant.toCelcius
import com.example.listingapp.databinding.WeatherReportBinding
import com.example.listingapp.model.WeatherRepository
import com.example.listingapp.viewModel.WeatherViewModel
import com.example.listingapp.viewModel.WeatherViewModelFactory

class WeatherReport : Fragment() {

    lateinit var viewModel: WeatherViewModel
    lateinit var binding: WeatherReportBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val networkService = WeatherNetworkService.getInstance()
        val weatherRepository = WeatherRepository(networkService)
        val lat = arguments?.get("lat").toString();
        val lng = arguments?.get("long").toString();

//        findNavController().popBackStack()


        binding = WeatherReportBinding.inflate(inflater, container,false)

        viewModel = ViewModelProvider(this, WeatherViewModelFactory(weatherRepository)).get(
            WeatherViewModel ::class.java)

        viewModel.getWeatherReport(lat = lat , lng = lng)

        viewModel.weatherData.observe(viewLifecycleOwner) {
            Log.e("WeatherData", viewModel.weatherData.value.toString())

            binding.temperature.text = toCelcius(it?.main?.temp)
        }

        return binding.root
    }


}