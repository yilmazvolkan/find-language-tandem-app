package com.yilmazvolkan.languagetandem.repository

import com.yilmazvolkan.languagetandem.data.api.TandemRemoteDataSource
import javax.inject.Inject

class TandemRepository @Inject constructor(
    private val remoteDataSource: TandemRemoteDataSource
    //,private val localDataSource: DataDao
) {
    fun getTandems(page: Int) = remoteDataSource.getTandems(page)

    /*performGetOperation(
    databaseQuery = { localDataSource.findAll() },
    networkCall = { remoteDataSource.getTandems(page) },
    saveCallResult = { localDataSource.insertAll(it.response.toList().toDataEntityList()) }
) */
}