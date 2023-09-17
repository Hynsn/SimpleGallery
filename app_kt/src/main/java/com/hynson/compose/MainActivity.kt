package com.hynson.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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
    val viewModel by lazy {
        MainViewModel()
    }

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewmodel: PagingViewModel = viewModel()

            val navController = rememberNavController()
            val contents = viewModel.getContents(this, navController)
            NavHost(navController = navController, startDestination = NaviConst.CONTENT) {
                composable(NaviConst.CONTENT) {
                    Content(contents)
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
fun Content(
    contents: MutableList<ContentItem>
) {
    val shape = RoundedCornerShape(5.dp)
    Column(
        modifier = Modifier
            .background(Color(0xFF063142))
            .padding(16.dp)
            .fillMaxHeight()
    ) {
        val fontSize = 18.sp
        for (item in contents) {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .background(color = Color(item.colors.first), shape = shape)
                    .clickable(onClick = item.action),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = item.name,
                    color = Color(item.colors.second),
                    fontSize = fontSize,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}
