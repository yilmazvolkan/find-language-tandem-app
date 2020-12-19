package com.yilmazvolkan.languagetandem.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TandemApplication : Application() {

    companion object {
        lateinit var instance: TandemApplication
    }

    init {
        instance = this
    }
}
