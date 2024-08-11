package com.murad.palindrome.data.di

import android.content.Context
import com.murad.palindrome.data.remote.api.ApiConfig
import com.murad.palindrome.data.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository(apiService)
    }
}