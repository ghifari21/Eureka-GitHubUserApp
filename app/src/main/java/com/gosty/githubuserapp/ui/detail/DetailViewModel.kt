package com.gosty.githubuserapp.ui.detail

import androidx.lifecycle.ViewModel
import com.gosty.githubuserapp.data.repositories.UserRepository

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUserByUsername(username: String) = userRepository.getUserByUsername(username)
}