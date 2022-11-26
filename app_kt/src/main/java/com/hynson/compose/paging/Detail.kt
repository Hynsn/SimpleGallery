package com.hynson.compose.paging

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.hynson.compose.NaviConst
import com.hynson.compose.ParamsConst

/**
 * Author: Hynsonhou
 * Date: 2022/11/25 14:50
 * Description: 详情
 * History:
 * <author>  <time>     <version> <desc>
 * Hynsonhou  2022/11/25   1.0       首次创建
 */
@Composable
fun DetailScaffold(title: String, id: Int, viewmodel: PagingViewModel) {
    val viewStates = remember {
        viewmodel.viewState
    }

    val data = viewStates.pagingData.collectAsLazyPagingItems()
    var text = "无数据"

    if (data.itemCount > 0) {
        text = data[id]?.desc ?: ""
    }
    Scaffold(topBar = {
        TopAppBar(title = { Text(title) })
    }, content = {
        val paddingValues = it

        Text(text = text)
    })
}