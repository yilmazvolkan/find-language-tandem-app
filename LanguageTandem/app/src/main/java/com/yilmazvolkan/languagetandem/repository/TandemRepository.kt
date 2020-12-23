package com.yilmazvolkan.languagetandem.repository

import com.yilmazvolkan.languagetandem.api.TandemRemoteDataSource
import javax.inject.Inject

class TandemRepository @Inject constructor(
    private val remoteDataSource: TandemRemoteDataSource
) {
    fun getTandems(page: Int) = remoteDataSource.getTandems(page)
}