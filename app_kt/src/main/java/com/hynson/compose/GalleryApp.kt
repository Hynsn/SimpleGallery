package com.hynson.compose

import android.app.Application
import com.hynson.network.base.BaseNetworkApi

class GalleryApp : Application() {
    override fun onCreate() {
        super.onCreate()

        BaseNetworkApi.init(NetworkRequestInfo(this))

    }
}