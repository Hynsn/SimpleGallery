package com.hynson.compose.paging

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import androidx.paging.compose.itemsIndexed
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hynson.compose.NaviConst
import kotlinx.coroutines.launch

@Composable
fun PagingScaffold(navController: NavHostController, viewmodel: PagingViewModel) {
    val TAG = "加载状态"
    val viewStates = remember {
        viewmodel.viewState
    }

    val data = viewStates.pagingData.collectAsLazyPagingItems()
    val refreshState = rememberSwipeRefreshState(isRefreshing = false)

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var lastRefresh = true

    Scaffold(scaffoldState = scaffoldState, topBar = {
        TopAppBar(title = { Text("分页") })
    }, content = {
        val paddingValues = it
        SwipeRefresh(state = refreshState, onRefresh = {
            data.refresh()
        }) {
            // 刷新：无缓存数据时
            if (data.loadState.refresh is LoadState.Error && data.itemCount <= 0) {
                ErrorContent() {
                    data.retry()
                }
            } else {
                LazyColumn() {
                    itemsIndexed(items = data) { i, item ->
                        Message(data = item, click = {
                            navController.navigate("${NaviConst.DETAIL}/${item?.title}/$i")
                        })
                    }
                    /*items(items = data) { item ->
                        Message(data = item, click = {
                            navController.navigate("${NaviConst.DETAIL}/${item?.title}")
                        })
                    }*/

                    data.run {
                        when {
                            // 加载更多：正在加载
                            loadState.append is LoadState.Loading -> {
                                item {
                                    LoadingItem()
                                }
                            }
                            // 加载更多：出错
                            loadState.append is LoadState.Error -> {
                                item {
                                    ErrorItem() {
                                        data.retry()
                                    }
                                }
                            }

                            // 刷新：有缓存数据时，Toast提示
                            loadState.refresh is LoadState.Error && data.itemCount > 0 -> {
                                if (lastRefresh) {
                                    lastRefresh = false
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(
                                            "刷新失败",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }

                            }
                            loadState.refresh is LoadState.Loading && data.itemCount > 0 -> {
                                lastRefresh = true
                            }
                        }
                    }
                    if (data.loadState.refresh is LoadState.Error) {
                        val error = (data.loadState.refresh as LoadState.Error).error
                        Log.d(TAG, "$error counter ${data.itemCount}")
                    }
                }
            }
        }
    },
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    snackbarData = data,
                    backgroundColor = Color.Blue,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
            }
        }
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Message(data: DatasBean?, click: () -> Unit) {
    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(10.dp)
            .fillMaxSize()
            .combinedClickable(onClick = click),
        elevation = 10.dp
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = "作者：${data?.author}"
            )
            Text(text = "${data?.title}")
        }
    }
}

@Composable
fun ErrorItem(retry: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "请求出错啦!")
        Button(onClick = { retry() }, modifier = Modifier.padding(10.dp)) {
            Text(text = "加载更多~")
        }
    }
}

@Composable
fun ErrorContent(retry: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(text = "请求出错啦!")
        Button(onClick = { retry() }, modifier = Modifier.padding(10.dp)) {
            Text(text = "重试")
        }
    }
}

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.padding(10.dp))
    }
}
