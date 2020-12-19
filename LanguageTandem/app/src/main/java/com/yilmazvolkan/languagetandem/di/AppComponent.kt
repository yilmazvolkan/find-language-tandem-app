package com.yilmazvolkan.languagetandem.di

import com.yilmazvolkan.languagetandem.repository.TandemRepository
import com.yilmazvolkan.languagetandem.ui.fragments.CommunityFragment
import com.yilmazvolkan.languagetandem.ui.fragments.CommunityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(tandemRepository: TandemRepository)

    fun inject(viewModel: CommunityViewModel)

    fun inject(communityFragment: CommunityFragment)
}