package com.yilmazvolkan.languagetandem.api

import com.squareup.moshi.Json
import com.yilmazvolkan.languagetandem.models.TandemData

data class ResponseResult(
    @field:Json(name = "response") val response: List<TandemData>,
    @field:Json(name = "errorCode") val errorCode: String,
    @field:Json(name = "type") val type: String
)