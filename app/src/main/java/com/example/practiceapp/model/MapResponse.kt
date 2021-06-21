package com.example.practiceapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MapResponse(
    @Json(name = "s2graph") val s2graph: VisitorInfo
)

@JsonClass(generateAdapter = true)
data class VisitorInfo(
    @Json(name = "status") val status: String,
    @Json(name = "day") val day: DayInfo
)

@JsonClass(generateAdapter = true)
data class DayInfo(
    @Json(name = "initData") val initData: List<Int>,
    @Json(name = "labels") val labels: List<String>,
    @Json(name = "avg") val avg: List<Int>,
    @Json(name = "sunday") val sunday: List<Int>,
    @Json(name = "monday") val monday: List<Int>,
    @Json(name = "tuesday") val tuesday: List<Int>,
    @Json(name = "wednesday") val wednesday: List<Int>,
    @Json(name = "thursday") val thursday: List<Int>,
    @Json(name = "friday") val friday: List<Int>,
    @Json(name = "saturday") val saturday: List<Int>,
    @Json(name = "max") val max: Int,
)