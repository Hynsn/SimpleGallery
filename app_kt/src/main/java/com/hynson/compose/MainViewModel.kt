package com.hynson.compose

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController

class MainViewModel() : ViewModel() {
    fun getContents(context: Context, navController: NavHostController): MutableList<ContentItem> {
        return arrayListOf(
            ContentItem("基础用法") {
                navController.navigate(NaviConst.COMMON)
            },
            ContentItem("列表") {
                navController.navigate(NaviConst.LAZY_COLUMN)
            },
            ContentItem("脚手架") {
                navController.navigate(NaviConst.SCAFFOLD)

            },
            ContentItem("分页") {
                navController.navigate(NaviConst.PAGING)
            },
            ContentItem("底部弹框") {
                navController.navigate(NaviConst.MODAL_BOTTOMSHEET)
            },
            ContentItem("沉浸式状态栏") {
                val intent = Intent(context, StatusBarActivity::class.java)
                ContextCompat.startActivity(context, intent, null)
            }
        )
    }
}