package com.example.practiceapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfoResponse(
    @Json(name = "users") val users: List<UserInfo>
)

@JsonClass(generateAdapter = true)
data class UserInfo(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "phone") val phone: String,
    @Json(name = "avatarImg") val avatarImg: String,
    @Json(name = "img") val img: String,
    @Json(name = "catImg") val catImg: String,
    @Json(name = "time") val time: String

)