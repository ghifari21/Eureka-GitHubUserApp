package com.gosty.githubuserapp.data.remote.network

import com.gosty.githubuserapp.data.remote.responses.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/users")
    suspend fun getUsers(
        @Query("per_page") perPage: String = "100"
    ): List<UserResponse>

    @GET("/users/{username}")
    suspend fun getUserByUsername(
        @Path("username") username: String
    ): UserResponse

    @GET("/users/{username}/{type}")
    suspend fun getUserFollow(
        @Path("username") username: String,
        @Path("type") type: String
    ): List<UserResponse>
}