package com.yilmazvolkan.languagetandem.api

import com.yilmazvolkan.languagetandem.models.TandemData

data class ResponseResult(
    val response: List<TandemData>,
    val errorCode: String,
    val type: String
)