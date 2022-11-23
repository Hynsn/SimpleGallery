package com.hynson.compose.paging

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig

class PagingViewModel : ViewModel() {

    /**
     * 获取数据
     */
    fun getData() = Pager(PagingConfig(pageSize = 8)) {
        ADataSource()
    }.flow
}