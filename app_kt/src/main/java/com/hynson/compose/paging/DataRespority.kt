package com.hynson.compose.paging

import com.hynson.compose.api.DataApi
import com.hynson.compose.api.RetrofitService
import com.hynson.network.WanAndroidNetworkApi

class DataRespority {
    private var netWork = RetrofitService.createService(DataApi::class.java)

//    private val netWork = WanAndroidNetworkApi.getService(DataApi::class.java)

    /**
     * 查询数据
     */
    suspend fun loadData(pageId: Int): DemoReqData {
        return netWork.getData(pageId)
    }
}