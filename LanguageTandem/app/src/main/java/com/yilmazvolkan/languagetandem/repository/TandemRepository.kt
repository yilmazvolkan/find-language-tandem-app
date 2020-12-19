package com.yilmazvolkan.languagetandem.repository

import com.yilmazvolkan.languagetandem.data.api.TandemRemoteDataSource
import com.yilmazvolkan.languagetandem.data.database.DataDao
import com.yilmazvolkan.languagetandem.data.database.toDataEntityList
import com.yilmazvolkan.languagetandem.di.DaggerAppComponent
import javax.inject.Inject

class TandemRepository @Inject constructor(
    private val remoteDataSource: TandemRemoteDataSource,
    private val localDataSource: DataDao
) {

    init {
        DaggerAppComponent.create().inject(this)
    }

    fun getTandems(page: Int) = performGetOperation(
        databaseQuery = { localDataSource.getAllTandems() },
        networkCall = { remoteDataSource.getTandems(page) },
        saveCallResult = { localDataSource.insertAll(it.response.toList().toDataEntityList()) }
    )
}