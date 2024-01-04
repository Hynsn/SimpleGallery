package com.hynson.compose

/**
 * Author: Hynsonhou
 * Date: 2023/12/29 14:30
 * Description: 滚轮测试页
 * History:
 * <author> <time> <version> <desc>
 * Hynsonhou 2023/12/29 1.0 首次创建
 */

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.SemanticsProperties.ContentDescription
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.focused
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hynson.compose.view.DataTimePicker
import com.hynson.compose.view.ListNumberPicker
import com.hynson.compose.view.WheelPicker
import kotlinx.coroutines.launch
import java.util.LinkedList

@Composable
fun WheelPickerScreen2() {
    DataTimePicker()
}

@Composable
fun WheelPickerScreen1() {
    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        Arrangement.Center,
        Alignment.CenterVertically
    ) {
        //年
        var selectYear by remember {
            mutableIntStateOf(2023)
        }

        val yearData = LinkedList<Int>().apply {
            for (i in 2000..2024) {
                add(i)
            }
        }

        ListNumberPicker(
            data = yearData,
            selectIndex = yearData.indexOf(selectYear),
            visibleCount = 5,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.background).copy(alpha = 0.3f),
                    shape = RoundedCornerShape(10.dp)
                )
                .semantics(mergeDescendants = true) {
                    contentDescription = "test"
                },
            onSelect = { _, item ->
                selectYear = item
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                /*.semantics(mergeDescendants = true) {
                    contentDescription = "test"
                    stateDescription = "hello"
                }*/,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //判断是否是选中的状态，选中要展示的样式和非选中的样式
                if (it == selectYear) {
                    Text(
                        modifier = Modifier.clearAndSetSemantics {
                            stateDescription = "$it"
                        },
                        text = "$it", color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 29.sp
                    )
                } else {
                    Text(
                        text = "$it",
                        modifier = Modifier.clearAndSetSemantics {
                        },
                        color = Color.White.copy(alpha = 0.3f),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                    )
                }
            }
        }

    }


}

@Composable
fun WheelPickerScreen() {
    val hour = remember { mutableStateOf("") }
    val minute = remember { mutableStateOf("") }
    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
    val subMinuteList = mutableStateListOf<String>()
    subMinuteList.addAll(arrayListOf("1", "2", "3", "4"))
    SnackbarHost(hostState = snackState, Modifier)
    Column {
        Box(modifier = Modifier.weight(1f))
        Row(
            Modifier
                .background(colorResource(R.color.white))
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { },
                modifier = Modifier
                    .width(50.dp)
                    .height(25.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.gray),
                    contentColor = colorResource(R.color.text_666)
                ),
                elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp),
                border = BorderStroke(1.dp, colorResource(R.color.gray)),
                contentPadding = PaddingValues(5.dp, 3.dp, 5.dp, 3.dp)
            ) {
                Text(text = "取消", fontSize = 13.sp)
            }
            Spacer(Modifier.weight(1f))
            Button(
                onClick = {
                    snackScope.launch {
                        snackState.showSnackbar(
                            "Selected date timestamp: ${hour.value} / ${minute.value}"
                        )
                    }
                },
                modifier = Modifier
                    .width(50.dp)
                    .height(25.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.theme_orange),
                    contentColor = colorResource(R.color.text_fff)
                ),
                elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp),
                border = BorderStroke(1.dp, colorResource(R.color.gray)),
                contentPadding = PaddingValues(5.dp, 3.dp, 5.dp, 3.dp)
            ) {
                Text(text = "确定", fontSize = 13.sp)
            }
        }
        HorizontalDivider()
        Box(
            modifier = Modifier
                .background(colorResource(R.color.white))
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(175.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val pickerModifier = Modifier
                    .weight(1f)
                    .height(175.dp)
                WheelPicker(
                    data = (0..23).map { it.toString().padStart(2, '0') },
                    selectIndex = 0,
                    visibleCount = 5,
                    modifier = pickerModifier,
                    onSelect = { _, item ->
                        hour.value = item
                    }
                ) {
                    Text(
                        text = it,
                        fontSize = 14.sp
                    )
                }
                Text(
                    text = "/",
                    fontSize = 15.sp
                )
                WheelPicker(
                    data = subMinuteList,
                    selectIndex = 0,
                    visibleCount = 5,
                    modifier = pickerModifier,
                    onSelect = { _, item ->
                        minute.value = item
                    }
                ) {
                    Text(
                        text = it,
                        fontSize = 14.sp
                    )
                }
            }
            Column(Modifier.height(175.dp)) {
                val whiteMaskModifier = Modifier
                    .background(colorResource(R.color.bb_white))
                    .fillMaxWidth()
                    .weight(1f)
                Spacer(whiteMaskModifier)
                Spacer(
                    Modifier
                        .background(colorResource(R.color.one_b_black), RoundedCornerShape(5.dp))
                        .fillMaxWidth()
                        .height(30.dp)
                )
                Spacer(whiteMaskModifier)
            }
        }
    }
}