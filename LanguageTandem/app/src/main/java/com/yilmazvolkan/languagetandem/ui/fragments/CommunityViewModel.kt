package com.yilmazvolkan.languagetandem.ui.fragments

import androidx.lifecycle.ViewModel
import com.yilmazvolkan.languagetandem.repository.TandemRepository
import javax.inject.Inject


class CommunityViewModel @Inject constructor(
    private val repository: TandemRepository
) : ViewModel() {

    val tandems = repository.getTandems(1)
}
