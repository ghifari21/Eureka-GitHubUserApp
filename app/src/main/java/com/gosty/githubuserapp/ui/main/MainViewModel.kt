package com.gosty.githubuserapp.ui.main

import androidx.lifecycle.ViewModel
import com.gosty.githubuserapp.data.repositories.UserRepository

class MainViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    fun getUsers() = userRepository.getUsers()
}