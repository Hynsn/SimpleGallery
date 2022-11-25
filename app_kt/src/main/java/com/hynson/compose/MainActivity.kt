package com.hynson.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hynson.compose.paging.DetailScaffold
import com.hynson.compose.paging.PagingScaffold
import com.hynson.compose.scaffold.ScaffoldTest

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //    Image(
            //        painter = painterResource(id = R.drawable.ic_launcher_background),
            //        contentDescription = "popular.title",
            //        contentScale = ContentScale.Crop,
            //        modifier = Modifier
            //            .padding(8.dp)
            //            .size(30.dp)
            //            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
            //    )
            //            Log.i("TAG", "setContent: ${this.javaClass}")
            //            SimpleGalleryTheme {
            //
            //            }
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = NaviConst.CONTENT) {
                composable(NaviConst.CONTENT) {
                    Content(navController)
                }
                composable(NaviConst.LAZY_COLUMN) {
                    SimpleList(title = stringResource(id = R.string.device_settings), onBack = {

                    })
                }
                composable(NaviConst.PAGING) {
                    PagingScaffold(navController)
                }
                composable(NaviConst.COMMON) {
                    Contact()
                }
                composable(NaviConst.SCAFFOLD) {
                    ScaffoldTest()
                }
                composable(NaviConst.DETAIL){
                    DetailScaffold(navController)
                }
            }
        }
    }
}

@Composable
fun Content(navController: NavHostController) {
    Column(
        modifier = Modifier
            .background(Color(0xFF063142))
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        val fontSize = 18.sp
        Text(
            text = "基础用法", fontSize = fontSize, modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color(0xFFFFFF00))
                .clickable {
                    navController.navigate(NaviConst.COMMON)
                }
        )
        Text(
            text = "列表", fontSize = fontSize, modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color(0xFF4385F4))
                .clickable {
                    navController.navigate(NaviConst.LAZY_COLUMN)
                }
        )
        Text(
            text = "脚手架", fontSize = fontSize, modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color(0xFF3CDB85))
                .clickable {
                    navController.navigate(NaviConst.SCAFFOLD)
                }
        )
        Text(
            text = "分页", fontSize = fontSize, modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color(0xFF3CDB85))
                .clickable {
                    navController.navigate(NaviConst.PAGING)
                }
        )
    }
}