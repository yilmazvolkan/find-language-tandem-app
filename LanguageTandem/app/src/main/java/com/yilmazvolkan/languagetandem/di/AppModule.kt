package com.yilmazvolkan.languagetandem.di

import com.yilmazvolkan.languagetandem.app.TandemApplication
import com.yilmazvolkan.languagetandem.data.api.TandemApi
import com.yilmazvolkan.languagetandem.data.api.TandemApiService
import com.yilmazvolkan.languagetandem.data.database.TandemDatabase
import com.yilmazvolkan.languagetandem.repository.TandemRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideApi(): TandemApi = TandemApiService.getClient()

    @Provides
    fun provideTandemRepository() = TandemRepository()

    @Singleton
    @Provides
    fun provideApplication(): TandemApplication = TandemApplication.instance

    @Singleton
    @Provides
    fun provideDB(application: TandemApplication) = TandemDatabase.invoke(application)

    @Provides
    fun provideDao(tandemDatabase: TandemDatabase) = tandemDatabase.dataDao()
}
