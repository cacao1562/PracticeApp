package com.example.practiceapp.api

import com.example.practiceapp.model.MapResponse
import com.example.practiceapp.model.UserResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users")
    suspend fun fetchUsers() : ApiResponse<UserResponse>

    @GET("main/v/{cid}")
    suspend fun fetchMapInfo(@Path("cid") cid: String) : ApiResponse<MapResponse>
}