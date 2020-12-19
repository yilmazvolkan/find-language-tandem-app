package com.yilmazvolkan.languagetandem.models

data class TandemData(
    val topic: String,
    val firstName: String,
    val pictureUrl: String,
    val natives: List<String>,
    val learns: List<String>,
    val referenceCnt: Int
)