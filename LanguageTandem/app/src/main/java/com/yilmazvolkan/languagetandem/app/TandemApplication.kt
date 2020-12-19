package com.yilmazvolkan.languagetandem.app

import android.app.Application

class TandemApplication : Application() {

    companion object {
        lateinit var instance: TandemApplication
    }

    init {
        instance = this
    }
}
