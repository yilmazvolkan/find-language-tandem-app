package com.yilmazvolkan.languagetandem.ui.fragments

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.yilmazvolkan.languagetandem.repository.TandemRepository


class CommunityViewModel @ViewModelInject constructor(
    private val repository: TandemRepository
) : ViewModel() {

    val tandems = repository.getTandems(1)
}
