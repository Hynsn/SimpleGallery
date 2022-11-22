package com.hynson.compose.paging

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig

/**
 * @author huanglinqing
 * @project Paging3Demo
 * @date 2020/11/7
 * @desc viewModel 对象
 */
class PagingViewModel : ViewModel() {

    /**
     * 获取数据
     */
    fun getData() = Pager(PagingConfig(pageSize = 8)) {
        ADataSource()
    }.flow
}