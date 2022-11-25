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
import com.hynson.compose.NaviConst

/**
 * Author: Hynsonhou
 * Date: 2022/11/25 14:50
 * Description: 详情
 * History:
 * <author>  <time>     <version> <desc>
 * Hynsonhou  2022/11/25   1.0       首次创建
 */
@Composable
fun DetailScaffold(navController: NavHostController) {
    val bean = navController.currentBackStackEntry?.arguments?.getParcelable<DatasBean>(NaviConst.DataBean)
    var name by remember { mutableStateOf("") }
    bean?.desc?.let {
        name = it
    }
    Scaffold(topBar = {
        TopAppBar(title = { Text("详情") })
    }, content = {
        val paddingValues = it

        Text(text = name)
    })
}