package com.yilmazvolkan.languagetandem.data.api

import javax.inject.Inject

class TandemRemoteDataSource @Inject constructor(
    private val tandemService: TandemService
) {
    fun getTandems(page: Int) = tandemService.getTandemCommunity(page)
}