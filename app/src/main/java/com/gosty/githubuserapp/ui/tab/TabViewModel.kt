package com.gosty.githubuserapp.ui.tab

import androidx.lifecycle.ViewModel
import com.gosty.githubuserapp.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TabViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    fun getUserFollow(username: String, type: String) = userRepository.getUserFollow(username, type)
}