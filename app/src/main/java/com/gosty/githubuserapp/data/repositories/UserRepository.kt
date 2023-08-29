package com.gosty.githubuserapp.data.repositories

import androidx.lifecycle.LiveData
import com.gosty.githubuserapp.data.models.UserModel
import com.gosty.githubuserapp.utils.Result

interface UserRepository {
    fun getUsers(): LiveData<Result<List<UserModel>>>
    fun getUserByUsername(username: String): LiveData<Result<UserModel>>
    fun getUserFollow(username: String, type: String): LiveData<Result<List<UserModel>>>
}