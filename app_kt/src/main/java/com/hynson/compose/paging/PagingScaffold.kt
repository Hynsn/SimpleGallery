package com.hynson.compose.paging

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hynson.compose.NaviConst
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun PagingScaffold(navController: NavHostController, viewModel: PagingViewModel) {
    val viewStates = remember {
        viewModel.viewState
    }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var lastRefresh = true

    Scaffold(scaffoldState = scaffoldState, topBar = {
        TopAppBar(title = { Text("分页") })
    }, content = {
        val paddingValues = it

        PagingPullRefresh(items = viewStates.pagingData) { lazyPagingItems ->
            LazyColumn() {
                items(lazyPagingItems.itemCount) { index ->
                    val item = lazyPagingItems[index] ?: return@items
                    Message(data = item, click = {
                        navController.navigate("${NaviConst.DETAIL}/${item.title}/$index")
                    })
                }
                lazyPagingItems.run {
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
                                    lazyPagingItems.retry()
                                }
                            }
                        }

                        // 刷新：有缓存数据时，Toast提示
                        loadState.refresh is LoadState.Error && lazyPagingItems.itemCount > 0 -> {
                            if (lastRefresh) {
                                lastRefresh = false
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        "下拉刷新失败",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        }

                        loadState.refresh is LoadState.Loading && lazyPagingItems.itemCount > 0 -> {
                            lastRefresh = true
                        }
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
fun ErrorContent(error: LoadState.Error, retry: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(text = "请求出错啦!\n${error.error.cause}", textAlign = TextAlign.Center)
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T : Any> PagingPullRefresh(
    modifier: Modifier = Modifier,
    items: Flow<PagingData<T>>,
    onRefresh: (() -> Unit)? = null,
    content: @Composable (LazyPagingItems<T>) -> Unit,
) {
    val pagingItems = items.collectAsLazyPagingItems()

    val refreshing = pagingItems.loadState.refresh is LoadState.Loading
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            pagingItems.refresh()
            onRefresh?.invoke()
        }
    )

    Box(
        modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        // 加载失败且无缓存数据时
        if (pagingItems.loadState.refresh is LoadState.Error && pagingItems.itemCount <= 0) {
            ErrorContent(pagingItems.loadState.refresh as LoadState.Error) {
                pagingItems.retry()
            }
        } else {
            content(pagingItems)
        }
        PullRefreshIndicator(
            modifier = Modifier.align(alignment = Alignment.TopCenter),
            refreshing = refreshing,
            state = pullRefreshState,
        )
    }
}