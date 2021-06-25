package com.example.practiceapp.api

import com.example.practiceapp.model.UserInfoResponse
import com.example.practiceapp.model.UserResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun fetchUsers() : ApiResponse<UserResponse>

    @GET("users")
    suspend fun fetchUsersInfo(@Query("page") page: Int) : ApiResponse<UserInfoResponse>

}