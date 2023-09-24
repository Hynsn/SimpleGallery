package com.hynson.network

import com.hynson.network.base.BaseNetworkApi
import okhttp3.Interceptor

object WanAndroidNetworkApi : BaseNetworkApi() {

    override fun getInterceptor(): Interceptor? {
        return null
    }

    override fun getFormal(): String {
        return "https://www.wanandroid.com/"
    }

    override fun getTest(): String {
        return "https://www.wanandroid.com/"
    }
}