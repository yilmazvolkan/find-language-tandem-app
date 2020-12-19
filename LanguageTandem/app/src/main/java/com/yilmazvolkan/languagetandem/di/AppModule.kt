package com.yilmazvolkan.languagetandem.di

import com.yilmazvolkan.languagetandem.app.TandemApplication
import com.yilmazvolkan.languagetandem.data.api.TandemApiService
import com.yilmazvolkan.languagetandem.data.api.TandemRemoteDataSource
import com.yilmazvolkan.languagetandem.data.api.TandemService
import com.yilmazvolkan.languagetandem.data.database.TandemDatabase
import com.yilmazvolkan.languagetandem.repository.TandemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideApplication(): TandemApplication = TandemApplication.instance

    @Singleton
    @Provides
    fun provideApi(): TandemService = TandemApiService.getClient()

    @Singleton
    @Provides
    fun provideRemoteDataSource(): TandemRemoteDataSource = TandemRemoteDataSource(provideApi())

    @Singleton
    @Provides
    fun provideDB() = TandemDatabase.invoke(provideApplication())

    @Singleton
    @Provides
    fun provideDao() = provideDB().dataDao()

    @Singleton
    @Provides
    fun provideTandemRepository() = TandemRepository(provideRemoteDataSource(), provideDao())
}
