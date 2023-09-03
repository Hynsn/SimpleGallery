package com.hynson.compose

import android.view.Window
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsHeight
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hynson.compose.bottomsheet.alertDialogTest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun statusTest(
    window: Window, lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {
    ProvideWindowInsets {
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setStatusBarColor(Color.Transparent, darkIcons = false)
        }
        /*        DisposableEffect(systemUiController) {
                    // When the effect leaves the Composition, remove the observer
                    onDispose {
                        //WindowCompat.setDecorFitsSystemWindows(window, true)
                    }
                }*/
        Surface(
            color = MaterialTheme.colors.background
        ) {
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
                            .padding(16.dp),
                    ) {
                        Text(text = "Hide sheet")
                    }
                    Spacer(
                        modifier = Modifier
                            .statusBarsHeight()
                            .fillMaxWidth()
                    )
                }) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(R.drawable.landscape),
                        contentDescription = "Background Image",
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth
                    )
                    Column {
                        com.google.accompanist.insets.ui.TopAppBar(
                            backgroundColor = Color.Transparent,
                            elevation = 0.dp,//阴影层级抬升 https://www.jianshu.com/p/c1d17a39bc09
                            title = {
                                Text(text = "首页")
                            },
                            contentPadding = rememberInsetsPaddingValues(
                                insets = LocalWindowInsets.current.statusBars
                            )
                        )
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
        }
    }
}