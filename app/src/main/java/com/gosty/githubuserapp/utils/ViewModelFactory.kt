package com.gosty.githubuserapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gosty.githubuserapp.data.repositories.UserRepository
import com.gosty.githubuserapp.di.Injection
import com.gosty.githubuserapp.ui.detail.DetailViewModel
import com.gosty.githubuserapp.ui.main.MainViewModel
import com.gosty.githubuserapp.ui.tab.TabViewModel

class ViewModelFactory private constructor(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(userRepository) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(userRepository) as T
            modelClass.isAssignableFrom(TabViewModel::class.java) -> TabViewModel(userRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }

    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideUserRepository()
                )
            }.also {
                instance = it
            }
    }
}