package com.hynson.compose.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.hynson.compose.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun CustomModalBottomSheetDemo() {
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        alertDialogTest(onDismissRequest = {
            showDialog = false
        }, leftClick = {
            showDialog = false

        }, rightClick = {
            showDialog = false
        }
        )
    }
    ModalBottomSheetLayout(sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetElevation = 16.dp,
        sheetBackgroundColor = Color.Green,
        sheetContent = {
            Text(
                text = "Hello from ModalBottomSheet",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h6
            )
            Button(
                onClick = { coroutineScope.launch { modalBottomSheetState.hide() } },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Hide sheet")
            }
        }) {
        Column {
            Button(
                onClick = {
                    coroutineScope.launch {
                        showDialog = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = "Show Dialog")
            }
            Button(
                onClick = {
                    coroutineScope.launch {
                        modalBottomSheetState.show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = "Show sheet")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ModalBottomDemo() {
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            Column {
                ListItem(
                    text = { Text("选择分享到哪里吧~") }
                )

                ListItem(
                    text = { Text("github") },
                    icon = {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF181717)
                        ) {
                            Icon(
                                painterResource(R.drawable.ic_launcher_background),
                                null,
                                tint = Color.White,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    },
                    modifier = Modifier.clickable { }
                )

                ListItem(
                    text = { Text("微信") },
                    icon = {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF07C160)
                        ) {
                            Icon(
                                painterResource(R.drawable.ic_launcher_foreground),
                                null,
                                tint = Color.White,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    },
                    modifier = Modifier.clickable { }
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { scope.launch { state.show() } }
            ) {
                Text("点我展开")
            }
        }
    }
}

@Preview
@Composable
fun alertDialogTest(onDismissRequest: () -> Unit, leftClick: () -> Unit, rightClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        buttons = {
            Row() {
                Button(
                    onClick = leftClick,
                    modifier = Modifier.weight(1f, true),
                    shape = RoundedCornerShape(bottomStart = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                ) {
                    Text(text = "取消")
                }
                Button(
                    onClick = rightClick,
                    modifier = Modifier.weight(1f, true),
                    shape = RoundedCornerShape(bottomEnd = 8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                ) {
                    Text(text = "确定")
                }
            }
        },
        title = {
            Text(text = "对话框标题")
        },
        text = {
            Text(text = "对话框内容对话框内容")
        },
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.White,
        contentColor = Color.Black,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun BottomSheetScaffold() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        sheetContent = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(128.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Swipe up to expand sheet")
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Sheet content")
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = {
                        scope.launch { scaffoldState.bottomSheetState.collapse() }
                    }
                ) {
                    Text("Click to collapse sheet")
                }
            }
        },
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Bottom sheet scaffold") },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                        Icon(Icons.Default.Menu, contentDescription = "Localized description")
                    }
                }
            )
        },
        floatingActionButton = {
            var clickCount by remember { mutableStateOf(0) }
            FloatingActionButton(
                onClick = {
                    // show snackbar as a suspend function
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Snackbar #${++clickCount}")
                    }
                }
            ) {
                Icon(Icons.Default.Favorite, contentDescription = "Localized description")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        sheetPeekHeight = 128.dp,
        drawerContent = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Drawer content")
                Spacer(Modifier.height(20.dp))
                Button(onClick = { scope.launch { scaffoldState.drawerState.close() } }) {
                    Text("Click to close drawer")
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            items(100) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(Color.Red)
                )
            }
        }
    }
}

