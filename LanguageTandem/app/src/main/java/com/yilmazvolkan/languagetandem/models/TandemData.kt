package com.yilmazvolkan.languagetandem.models

import com.squareup.moshi.Json
import java.util.*

data class TandemData(
    @field:Json(name = "topic") val topic: String,
    @field:Json(name = "firstName") val firstName: String,
    @field:Json(name = "pictureUrl") val pictureUrl: String,
    @field:Json(name = "natives") val natives: List<String>,
    @field:Json(name = "learns") val learns: List<String>,
    @field:Json(name = "referenceCnt") val referenceCnt: Int
) {
    fun isNew(): Boolean = (referenceCnt == 0)

    fun getLearnsString() = learns.joinToString().toUpperCase(Locale.ROOT)

    fun getNativeString() = natives.joinToString().toUpperCase(Locale.ROOT)
}