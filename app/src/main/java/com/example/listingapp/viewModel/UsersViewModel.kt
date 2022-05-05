package com.example.listingapp.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.listingapp.model.Result
import com.example.listingapp.model.Users
import com.example.listingapp.model.UsersRepository
import kotlinx.coroutines.*
import retrofit2.await

class UsersViewModel constructor(private val usersRepository: UsersRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val usersList = MutableLiveData<Users>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.localizedMessage?.let { Log.e("ERROR   ", it.toString()) }
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getRandomUsers(page: Int) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = usersRepository.getRandomUsers(page)
//            Log.e("ERROR",response.toString())
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Log.e("ERROR",response.toString())
                    usersList.postValue(response.body())
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