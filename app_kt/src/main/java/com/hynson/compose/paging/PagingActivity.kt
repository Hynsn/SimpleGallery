package com.hynson.compose.paging

import android.os.Bundle

import android.os.Message
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hynson.compose.scaffold.ScaffoldTest
import java.io.IOException

class PagingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            Surface(
//                modifier = Modifier.fillMaxSize(),
//                color = MaterialTheme.colors.background
//            ) {
//                Greeting()
//            }
            ScaffoldTest()
        }
    }
}

@Composable
fun Greeting() {
    val mainViewmodel: PagingViewModel = viewModel()
    val data = mainViewmodel.getData().collectAsLazyPagingItems()
    val refreshState = rememberSwipeRefreshState(isRefreshing = false)
    SwipeRefresh(state = refreshState, onRefresh = {
        data.refresh()
    }) {
        LazyColumn() {
            items(items = data) { item ->
                Message(data = item)
            }

            data.apply {
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
                    // 刷新：无缓存数据时
                    loadState.refresh is LoadState.Error && data.itemCount <= 0 -> {
                        item {
                            ErrorContent() {
                                data.retry()
                            }
                        }
                    }
                    // 刷新：有缓存数据时，Toast提示
                    loadState.refresh is LoadState.Error && data.itemCount > 0 -> {

                    }
                }
            }

//            val TAG = "加载状态"
//
//            if (data.loadState.refresh is LoadState.Loading) {
//                Log.d(TAG, "正在加载")
//            } else if (data.loadState.refresh is LoadState.Error) {
//                when ((data.loadState.refresh as LoadState.Error).error) {
//                    is IOException -> {
//                        Log.d(TAG, "网络未连接，可在这里放置失败视图")
//                    }
//                    else -> {
//                        Log.d(TAG, "网络未连接，其他异常")
//                    }
//                }
//            }
        }
    }
}

@Composable
fun Message(data: DatasBean?) {
    Card(
        modifier = Modifier
            .background(Color.White)
            .padding(10.dp)
            .fillMaxSize(), elevation = 10.dp
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
    Button(onClick = { retry() }, modifier = Modifier.padding(10.dp)) {
        Text(text = "重试")
    }
}

@Composable
fun ErrorContent(retry: () -> Unit) {
    Text(text = "请求出错啦")
    Button(onClick = { retry() }, modifier = Modifier.padding(10.dp)) {
        Text(text = "重试")
    }
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(modifier = Modifier.padding(10.dp))
}
