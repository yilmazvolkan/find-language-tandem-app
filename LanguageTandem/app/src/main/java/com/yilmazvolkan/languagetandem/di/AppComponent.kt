package com.yilmazvolkan.languagetandem.di

import com.yilmazvolkan.languagetandem.repository.TandemRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(tandemRepository: TandemRepository)
}