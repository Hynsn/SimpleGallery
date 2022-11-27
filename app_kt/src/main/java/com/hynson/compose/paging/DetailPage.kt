package com.hynson.compose.paging

import android.text.Html
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems

/**
 * Author: Hynsonhou
 * Date: 2022/11/25 14:50
 * Description: 详情
 * History:
 * <author>  <time>     <version> <desc>
 * Hynsonhou  2022/11/25   1.0       首次创建
 */
@Composable
fun DetailPage(title: String, id: Int, viewmodel: PagingViewModel) {
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
        val html = Html.fromHtml(text)
        Text(text = html.toString(), modifier = Modifier.verticalScroll(rememberScrollState()))
    })
}