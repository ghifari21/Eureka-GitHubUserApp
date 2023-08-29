package com.gosty.githubuserapp.ui.detail

import androidx.lifecycle.ViewModel
import com.gosty.githubuserapp.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    fun getUserByUsername(username: String) = userRepository.getUserByUsername(username)
}