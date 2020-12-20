package com.yilmazvolkan.languagetandem.models

import java.util.*

data class TandemData(
    val topic: String,
    val firstName: String,
    val pictureUrl: String,
    val natives: List<String>,
    val learns: List<String>,
    val referenceCnt: Int
) {
    fun isNew(): Boolean = (referenceCnt == 0)

    fun getLearnsString() = learns.joinToString().toUpperCase(Locale.ROOT)

    fun getNativeString() = natives.joinToString().toUpperCase(Locale.ROOT)
}