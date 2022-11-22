package com.hynson.compose.paging

class DataRespority {
    private var netWork = RetrofitService.createService(DataApi::class.java)

    /**
     * 查询数据
     */
    suspend fun loadData(pageId: Int): DemoReqData {
        return netWork.getData(pageId)
    }
}