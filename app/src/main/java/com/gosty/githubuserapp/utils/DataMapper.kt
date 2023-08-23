package com.gosty.githubuserapp.utils

import com.gosty.githubuserapp.data.models.UserModel
import com.gosty.githubuserapp.data.remote.responses.UserResponse

object DataMapper {
    fun mapUserResponseToUserModel(input: UserResponse): UserModel =
        UserModel(
            login = input.login,
            id = input.id,
            avatar = input.avatar,
            htmlUrl = input.htmlUrl,
            name = input.name,
            email = input.email,
            company = input.company,
            location = input.location,
            publicRepos = input.publicRepos,
            followers = input.followers,
            following = input.following
        )
}