package com.gosty.githubuserapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    val login: String,
    val id: Int,
    val avatar: String,
    val htmlUrl: String,
    val name: String?,
    val email: String?,
    val company: String?,
    val location: String?,
    val publicRepos: Int?,
    val followers: Int?,
    val following: Int?
): Parcelable
