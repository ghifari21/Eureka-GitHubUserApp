package com.gosty.githubuserapp.ui.tab

import androidx.lifecycle.ViewModel
import com.gosty.githubuserapp.data.repositories.UserRepository

class TabViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    fun getUserFollow(username: String, type: String) = userRepository.getUserFollow(username, type)
}