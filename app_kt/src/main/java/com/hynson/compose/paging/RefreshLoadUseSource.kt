/*
package com.hynson.compose.paging

import kotlinx.coroutines.delay

class RefreshLoadUseSource : PagingSource<Int, RefreshData>() {
    override fun getRefreshKey(state: PagingState<Int, RefreshData>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RefreshData> {
        return try {
            val nextPage = params.key ?: 1
            if (nextPage < 13) {
                delay(1500)
                val datas = mutableListOf(
                    RefreshData("哈哈${params.key}"),
                    RefreshData("哈哈${params.key}"),
                    RefreshData("哈哈${params.key}"),
                    RefreshData("哈哈${params.key}"),
                    RefreshData("哈哈${params.key}")
                )
                LoadResult.Page(
                    data = datas,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = if (nextPage < 100) nextPage + 1 else null
                )
            } else {//超过13条就加载错误
                LoadResult.Error(NullPointerException("空指针"))
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

data class RefreshData(val data: String)

*/
