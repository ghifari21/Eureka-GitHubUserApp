package com.gosty.githubuserapp.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.gosty.githubuserapp.data.models.UserModel
import com.gosty.githubuserapp.data.remote.network.ApiService
import com.gosty.githubuserapp.utils.DataMapper
import com.gosty.githubuserapp.utils.Result

class UserRepository private constructor(
    private val apiService: ApiService
) {
    fun getUsers(): LiveData<Result<List<UserModel>>> = liveData {
        val usersList = MutableLiveData<List<UserModel>>()
        emit(Result.Loading)
        try {
            val response = apiService.getUsers()
            if (response.isNotEmpty()) {
                usersList.value = response.map {
                    DataMapper.mapUserResponseToUserModel(it)
                }
            } else {
                emit(Result.Empty)
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.e("UserRepository", e.message.toString())
        }
        val data: LiveData<Result<List<UserModel>>> = usersList.map {
            Result.Success(it!!)
        }
        emitSource(data)
    }

    fun getUserByUsername(username: String): LiveData<Result<UserModel>> = liveData {
        emit(Result.Loading)
        val user = MutableLiveData<UserModel>()
        try {
            val response = apiService.getUserByUsername(username)
            user.value = DataMapper.mapUserResponseToUserModel(response)
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.e("UserRepository", e.message.toString())
        }
        val data: LiveData<Result<UserModel>> = user.map {
            Result.Success(it!!)
        }
        emitSource(data)
    }

    fun getUserFollow(username: String, type: String): LiveData<Result<List<UserModel>>> = liveData {
        val usersList = MutableLiveData<List<UserModel>>()
        emit(Result.Loading)
        try {
            val response = apiService.getUserFollow(username, type)
            if (response.isNotEmpty()) {
                usersList.value = response.map {
                    DataMapper.mapUserResponseToUserModel(it)
                }
            } else {
                emit(Result.Empty)
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
            Log.e("UserRepository", e.message.toString())
        }
        val data: LiveData<Result<List<UserModel>>> = usersList.map {
            Result.Success(it!!)
        }
        emitSource(data)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(apiService: ApiService): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService)
            }.also {
                instance = it
            }
    }
}