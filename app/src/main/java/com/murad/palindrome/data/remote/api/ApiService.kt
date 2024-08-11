package com.murad.palindrome.data.remote.api

import com.murad.palindrome.data.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUserList(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UserResponse
}