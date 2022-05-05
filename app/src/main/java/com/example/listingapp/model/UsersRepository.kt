package com.example.listingapp.model

import com.example.listingapp.`interface`.NetworkService

class UsersRepository constructor(private val networkService: NetworkService) {

    suspend fun getRandomUsers(page:Int) = networkService.getRandomUsers(page)

}