package com.hynson.compose.paging

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

class PagingViewModel : ViewModel() {

    private val pager by lazy {
        Pager(PagingConfig(pageSize = 8)) {
            ADataSource()
        }.flow.cachedIn(viewModelScope)// 缓存数据
    }
    val viewState by mutableStateOf(ViewState(pager))
}

data class ViewState(
    val pagingData: Flow<PagingData<DatasBean>>
)