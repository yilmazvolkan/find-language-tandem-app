package com.yilmazvolkan.languagetandem.di

import com.yilmazvolkan.languagetandem.api.ApiService
import com.yilmazvolkan.languagetandem.api.TandemRemoteDataSource
import com.yilmazvolkan.languagetandem.api.TandemService
import com.yilmazvolkan.languagetandem.app.TandemApplication
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
    fun provideApi(): TandemService = ApiService.getClient()

    @Singleton
    @Provides
    fun provideRemoteDataSource(tandemService: TandemService): TandemRemoteDataSource =
        TandemRemoteDataSource(tandemService)

    @Singleton
    @Provides
    fun provideTandemRepository(remoteDataSource: TandemRemoteDataSource) =
        TandemRepository(remoteDataSource)
}
