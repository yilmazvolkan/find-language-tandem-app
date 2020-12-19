package com.yilmazvolkan.languagetandem.data.api

import javax.inject.Inject

class TandemRemoteDataSource @Inject constructor(
    private val tandemService: TandemService
) : BaseDataSource() {

    suspend fun getTandems(page: Int) = getResult { tandemService.getTandemCommunity(page) }
}