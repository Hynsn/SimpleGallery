package com.hynson.compose

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.hynson.compose.Utils.random
import kotlinx.coroutines.launch

/**
 * Author: Hynsonhou
 * Date: 2022/10/21 10:09
 * Description: Compose 简单列表
 * History:
 * <author>  <time>     <version> <desc>
 * Hynsonhou  2022/10/21   1.0       首次创建
 */
@Composable
fun SimpleList(title: String, onBack: (() -> Unit)) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h6
                    )
                }
            )
        },
    ) {
        LazyRowItemsDemo(it)
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun LazyRowItemsDemo(it: PaddingValues) {
    val list = mutableStateListOf<Contact>()
    list.addAll(mockData())
    val scope = rememberCoroutineScope()
    val state = rememberLazyListState()
    val alertDialog = remember { mutableStateOf(-1) }
    val longClicke: (Int) -> (Unit) = {
        alertDialog.value = it
    }
    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        state = state
    ) {
        //        list.forEach { contact ->
        //            item {
        //                ContactListItem(contact)
        //            }
        //        }
        //        items(list) {
        //            ContactListItem(it)
        //        }

        list.forEachIndexed { index, contact ->
            item {
                ContactListItem(index, longClicke, contact)
            }
        }
    }

    ShowDialog(alertDialog) { index ->
        scope.launch {
            if (index in 0..list.size)
                list.removeAt(index)
        }
    }

    //设置靠右下角
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 16.dp, bottom = 16.dp)
    ) {
        FloatingActionButton(onClick = {
            scope.launch {
                state.scrollToItem(0)
            }

        }) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
        }
        FloatingActionButton(onClick = {
            list.first().counter.value++
        }) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null
            )
        }
        FloatingActionButton(onClick = {
            scope.launch {
                val c = Color(0).random()
                list.add(Contact(1, c, random(), mutableStateOf(0)))
                val index = list.size - 1
                state.scrollToItem(index)
            }

        }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }
}

fun mockData(): List<Contact> {
    val list = mutableListOf<Contact>()
    for (i in 0..5) {
        val c = Color(0).random()
        list.add(Contact(5, c, random(), mutableStateOf(0)))
    }
    return list
}

class ContactDefault : PreviewParameterProvider<Contact> {
    override val values = mockData().asSequence()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactListItem(
    index: Int, onLongClick: ((Int) -> Unit)?,
    @PreviewParameter(ContactDefault::class) contact: Contact,
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .background(Color(0xFFF6F6F6))
            .padding(10.dp)
            .height(60.dp)
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(10.dp))
            .combinedClickable(onLongClick = {
                onLongClick?.invoke(index)
                //                Toast
                //                    .makeText(context, "$index 长按操作", Toast.LENGTH_SHORT)
                //                    .show()
            }, onClick = {
                Toast
                    .makeText(context, "点击操作", Toast.LENGTH_SHORT)
                    .show()
            })
            //            .clickable {
            //                Toast
            //                    .makeText(context, "contact:${contact.toString()}", Toast.LENGTH_SHORT)
            //                    .show()
            //            }
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "",
            modifier = Modifier
                .size(40.dp, 40.dp)
                .background(color = contact.userIcon, shape = CircleShape),
        )
        Column(Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)) {
            val counter = if (contact.counter.value > 0) contact.counter.value.toString() else ""
            Text(text = contact.userName, fontSize = 16.sp)
            Text(text = counter, fontSize = 10.sp, color = Color(0x52030303))
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ShowDialog(alertDialog: MutableState<Int>, onClick: (Int) -> Unit) {
    if (alertDialog.value >= 0) {
        AlertDialog(
            //当用户尝试通过单击外部或按下后退按钮来关闭对话框时执行。单击关闭按钮时不会调用此方法。
            onDismissRequest = {
                alertDialog.value = -1
            },
            //对话框的标题，它应该指定对话框的目的。标题不是强制性的，因为文本中可能有足够的信息。提供的文本样式默认为 Typography.h6
            title = {
                Text(text = "弹了个框")
            },
            //显示有关对话框目的的详细信息的文本。提供的文本样式默认为 Typography.body1
            text = {
                Text(
                    "是否获得屠龙宝刀一把" + "\n" +
                            "是否获得倚天神剑一把"
                )
            },
            //一个按钮，用于确认的操作，从而解决触发对话框的原因。该对话框不会为此按钮设置任何事件，因此它们需要由调用者设置。null则不显示
            confirmButton = {
                TextButton(
                    onClick = {
                        onClick.invoke(alertDialog.value)
                        alertDialog.value = -1
                    }
                ) {
                    Text("删除")
                }
            },
            //用于关闭对话框的按钮。该对话框不会为此按钮设置任何事件，因此它们需要由调用者设置,null则不显示
            dismissButton = {
                TextButton(
                    onClick = {
                        alertDialog.value = -1
                    }
                ) {
                    Text("大可不必")
                }
            },
            backgroundColor = Color.Gray,
            //此对话框为其子级提供的首选内容颜色。
            contentColor = Color.Red,
            //平台特定的属性，以进一步配置对话框
            properties = DialogProperties(
                //是否可以通过按下后退按钮来关闭对话框。 如果为 true，按下后退按钮将调用 onDismissRequest。
                dismissOnBackPress = true,
                //是否可以通过在对话框边界外单击来关闭对话框。 如果为 true，单击对话框外将调用 onDismissRequest
                dismissOnClickOutside = true,
                //用于在对话框窗口上设置 WindowManager.LayoutParams.FLAG_SECURE 的策略。
                securePolicy = SecureFlagPolicy.Inherit,
                //对话框内容的宽度是否应限制为平台默认值，小于屏幕宽度。
                usePlatformDefaultWidth = true
            )
        )
    }
}
