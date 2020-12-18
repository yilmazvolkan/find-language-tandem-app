package com.yilmazvolkan.languagetandem.data.api

import com.yilmazvolkan.languagetandem.data.models.TandemData

data class TandemResult(
    val response: List<TandemData>,
    val errorCode: String,
    val type: String
)