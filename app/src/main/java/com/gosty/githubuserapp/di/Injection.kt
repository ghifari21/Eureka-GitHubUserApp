package com.gosty.githubuserapp.di

import com.gosty.githubuserapp.data.remote.network.ApiConfig
import com.gosty.githubuserapp.data.repositories.UserRepository

object Injection {
    fun provideUserRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }
}