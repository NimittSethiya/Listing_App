package com.example.listingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.listingapp.`interface`.NetworkService
import com.example.listingapp.model.UsersRepository
import com.example.listingapp.viewModel.MainViewModelFactory
import com.example.listingapp.viewModel.UsersViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { false }
//        startSomeNextActivity()
//        finish()

        setContentView(R.layout.activity_main)
//        finish()

        val networkService = NetworkService.getInstance()
        val usersRepository = UsersRepository(networkService)

        viewModel = ViewModelProvider(this, MainViewModelFactory(usersRepository)).get(UsersViewModel ::class.java)

        viewModel.getRandomUsers(1);

        NavigationUI.setupActionBarWithNavController(this,findNavController(R.id.myNavHostFragment))
    }

    override fun onNavigateUp(): Boolean {
        return this.findNavController(R.id.myNavHostFragment).navigateUp()
    }
}