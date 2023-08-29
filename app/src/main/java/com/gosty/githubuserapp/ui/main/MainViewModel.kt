package com.gosty.githubuserapp.ui.main

import androidx.lifecycle.ViewModel
import com.gosty.githubuserapp.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    fun getUsers() = userRepository.getUsers()
}