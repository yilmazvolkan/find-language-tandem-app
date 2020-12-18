package com.yilmazvolkan.languagetandem.data.models

data class TandemData(
    val topic: String,
    val firstName: String,
    val pictureUrl: String,
    val natives: ArrayList<String>,
    val learns: ArrayList<String>,
    val referenceCnt: Int
)