package com.yilmazvolkan.languagetandem.di

import com.yilmazvolkan.languagetandem.app.TandemApplication
import com.yilmazvolkan.languagetandem.data.api.ApiService
import com.yilmazvolkan.languagetandem.data.api.TandemRemoteDataSource
import com.yilmazvolkan.languagetandem.data.api.TandemService
import com.yilmazvolkan.languagetandem.data.database.TandemDatabase
import com.yilmazvolkan.languagetandem.repository.CommunityDataSourceFactory
import com.yilmazvolkan.languagetandem.repository.TandemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.disposables.CompositeDisposable
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
    fun provideDB(application: TandemApplication) = TandemDatabase.invoke(application)

    @Singleton
    @Provides
    fun provideDao(tandemDatabase: TandemDatabase) = tandemDatabase.dataDao()

    @Singleton
    @Provides
    fun provideTandemRepository(remoteDataSource: TandemRemoteDataSource) =
        TandemRepository(remoteDataSource)

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

    @Singleton
    @Provides
    fun provideCommunityDataSourceFactory(
        tandemRepository: TandemRepository,
        compositeDisposable: CompositeDisposable
    ) =
        CommunityDataSourceFactory(tandemRepository, compositeDisposable)
}
