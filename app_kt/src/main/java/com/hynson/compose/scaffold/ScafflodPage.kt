package com.hynson.compose.scaffold

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun ScaffoldPage() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = { //抽屉组件
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "抽屉组件中内容")
            }
        },
        topBar = { //屏幕顶部的标题栏
            TopAppBar(title = { Text("脚手架") },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch { scaffoldState.drawerState.open() }
                    }) {
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                })
        },
        floatingActionButton = { //悬浮按钮
            Column {
                ExtendedFloatingActionButton(
                    text = { Text("Toast") },
                    onClick = {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                "这是一个Toast",
                                duration = SnackbarDuration.Short
                            )
                        }
                    })
                ExtendedFloatingActionButton(
                    text = { Text("SnackBar") },
                    onClick = {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(
                                "这是一个SnackBar",
                                "按钮",
                                SnackbarDuration.Short
                            )
                        }
                    })
            }
        },
        floatingActionButtonPosition = FabPosition.End, //悬浮按钮在屏幕中的位置
        content = {
            //屏幕内容
            val paddingValues = it.calculateBottomPadding()
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "屏幕内容区域")
            }
        },
        snackbarHost = {
            SnackbarHost(it) { data ->
//                Snackbar(
//                    snackbarData = data,
//                    backgroundColor = Color.Blue,
//                    contentColor = Color.White,
//                    shape = RoundedCornerShape(10.dp)
//                )
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Snackbar(
                        modifier = Modifier
                            .wrapContentHeight()
                            .width(200.dp)
                    ) {
                        Row(
                            Modifier
                                .wrapContentHeight()
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "",
                                tint = Color.White
                            )
                            Text(
                                text = data.message,
                                modifier = Modifier.padding(start = 10.dp),
                                style = TextStyle(color = Color.White, fontSize = 20.sp)
                            )
                        }
                    }
                }

            }
        }
    )
}