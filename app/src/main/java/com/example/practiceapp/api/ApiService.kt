package com.example.practiceapp.api

import com.example.practiceapp.model.UserResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun fetchUsers() : ApiResponse<UserResponse>
}