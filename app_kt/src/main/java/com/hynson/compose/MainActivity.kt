package com.hynson.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hynson.compose.bottomsheet.CustomModalBottomSheetDemo
import com.hynson.compose.paging.DetailPage
import com.hynson.compose.paging.PagingScaffold
import com.hynson.compose.paging.PagingViewModel
import com.hynson.compose.scaffold.ScaffoldPage

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val mainViewmodel: PagingViewModel = viewModel()
            val context = LocalContext.current

            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = NaviConst.CONTENT) {
                composable(NaviConst.CONTENT) {
                    Content(navController) {
                        val intent = Intent(context, StatusBarActivity::class.java)
                        ContextCompat.startActivity(context, intent, null)
                    }
                }
                composable(NaviConst.LAZY_COLUMN) {
                    SimpleList(title = stringResource(id = R.string.device_settings), onBack = {

                    })
                }
                composable(NaviConst.PAGING) {
                    PagingScaffold(navController, mainViewmodel)
                }
                composable(NaviConst.COMMON) {
                    Contact()
                }
                composable(NaviConst.SCAFFOLD) {
                    ScaffoldPage()
                }
                composable(NaviConst.MODAL_BOTTOMSHEET) {
//                    ModalBottomDemo()
                    CustomModalBottomSheetDemo()
                }
                composable(
                    route = "${NaviConst.DETAIL}/{${ParamsConst.TITLE}}/{${ParamsConst.ID}}",
                    arguments = listOf(
                        navArgument(ParamsConst.TITLE) {
                            type = NavType.StringType
                        },
                        navArgument(ParamsConst.ID) {
                            type = NavType.IntType
                        },
                    )
                ) {
                    val argument = requireNotNull(it.arguments)
                    val title = argument.getString(ParamsConst.TITLE)
                    val id = argument.getInt(ParamsConst.ID, 0)
                    DetailPage(title ?: "", id, mainViewmodel)
                }
            }
        }
    }
}

@Composable
fun Content(navController: NavHostController, onClick: () -> Unit) {
    val shape = RoundedCornerShape(5.dp)
    val style = Modifier
        .fillMaxWidth()
        .wrapContentHeight(Alignment.CenterVertically)
        .blur(5.dp)
        .height(40.dp)
        .background(Color(0xFFFFFF00), shape)
    Column(
        modifier = Modifier
            .background(Color(0xFF063142))
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        val fontSize = 18.sp
        Text(
            text = "基础用法", fontSize = fontSize, modifier = style.clickable {
                navController.navigate(NaviConst.COMMON)
            }
        )
        Text(
            text = "列表", fontSize = fontSize, modifier = style
                .background(Color(0xFF4385F4), shape)
                .clickable {
                    navController.navigate(NaviConst.LAZY_COLUMN)
                }
        )
        Text(
            text = "脚手架", fontSize = fontSize, modifier = style
                .background(Color(0xFF3CDB85), shape)
                .clickable {
                    navController.navigate(NaviConst.SCAFFOLD)
                }
        )
        Text(
            text = "分页", fontSize = fontSize, modifier = style
                .background(Color.Red, shape)
                .clickable {
                    navController.navigate(NaviConst.PAGING)
                }
        )
        Text(
            text = "底部弹框", fontSize = fontSize, modifier = style
                .background(Color.Red, shape)
                .clickable {
                    navController.navigate(NaviConst.MODAL_BOTTOMSHEET)
                }
        )
        Text(
            text = "沉浸式状态栏", fontSize = fontSize, modifier = style
                .background(Color.Red, shape)
                .clickable(onClick = onClick)
        )
    }
}