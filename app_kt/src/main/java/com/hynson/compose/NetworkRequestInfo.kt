package com.hynson.compose

import android.app.Application
import com.hynson.network.base.INetworkRequiredInfo

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
class NetworkRequestInfo(override val applicationContext: Application) : INetworkRequiredInfo {
    override val appVersionName: String
        get() = BuildConfig.VERSION_NAME
    override val appVersionCode: String
        get() = BuildConfig.VERSION_CODE.toString()
    override val isDebug: Boolean
        get() = BuildConfig.DEBUG
}