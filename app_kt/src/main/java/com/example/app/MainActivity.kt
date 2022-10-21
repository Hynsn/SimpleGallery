package com.example.app

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.example.app.ui.theme.SimpleGalleryTheme
import com.google.accompanist.glide.rememberGlidePainter
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            Log.i("TAG", "setContent: ${this.javaClass}")
//            SimpleGalleryTheme {
//
//            }
            Greeting(name = "title", onBack = {
                finish()
            })
        }
    }
}

@Composable
fun Greeting(name: String, onBack: (() -> Unit)) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.device_settings),
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

//    Image(
//        painter = painterResource(id = R.drawable.ic_launcher_background),
//        contentDescription = "popular.title",
//        contentScale = ContentScale.Crop,
//        modifier = Modifier
//            .padding(8.dp)
//            .size(30.dp)
//            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
//    )

@SuppressLint("UnrememberedMutableState")
@Composable
fun LazyRowItemsDemo(it: PaddingValues) {
    val list = mutableStateListOf<Contact>()
    list.addAll(mockData())
    val scope = rememberCoroutineScope()
    val state = rememberLazyListState()
    val alertDialog = remember { mutableStateOf(false) }


//    for (i in 1..100) {
//        list.add("项目 $i")
//    }
    // 指定宽度、高度
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
        val longClicke:(Int)->(Unit) = {
            alertDialog.value = true
        }
        list.forEachIndexed { index, contact ->
            item {
                ContactListItem(index, longClicke,contact)
            }
        }
    }
    showDialog(alertDialog)


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
                list.removeLast()
                val index = list.size - 1
                state.scrollToItem(index)
            }

        }) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null
            )
        }
        FloatingActionButton(onClick = {
            list.first().email.value = "ddd"
        }) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null
            )
        }
        FloatingActionButton(onClick = {
            scope.launch {
                //添加一个新的数据
                val time = System.currentTimeMillis()
                list.add(Contact(1, Color.Gray, time.toString(), mutableStateOf("test")))
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun showDialog(alertDialog: MutableState<Boolean>) {
    if (alertDialog.value) {
        AlertDialog(
            //当用户尝试通过单击外部或按下后退按钮来关闭对话框时执行。单击关闭按钮时不会调用此方法。
            onDismissRequest = {
                alertDialog.value = false
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
                        alertDialog.value = false
                    }
                ) {
                    Text("确认获取")
                }
            },
            //用于关闭对话框的按钮。该对话框不会为此按钮设置任何事件，因此它们需要由调用者设置,null则不显示
            dismissButton = {
                TextButton(
                    onClick = {
                        alertDialog.value = false
                    }
                ) {
                    Text("大可不必")
                }
            },
            //对话框的背景颜色
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

@Preview(showBackground = true)
@Composable
fun Content() {
    val imageRes = painterResource(id = R.drawable.ic_launcher_background)
    Row(
        modifier = Modifier
            .background(Color(0xFFBCE9FF))
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            imageRes,
            null,
            modifier = Modifier
                .padding(8.dp)
                .background(Color(0xFF343434))
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier.background(Color(0xFFE0FFE3)),
            horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier.background(Color(0xFFFFE5E0)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "万俟霜风",
                    style = TextStyle(
                        fontSize = 22.sp
                    ),
                    modifier = Modifier
                        .background(Color(0xFF868FBA))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "@罗德岛",
                    color = Color.Blue,
                    style = TextStyle(
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.background(Color(0xFF96BA86))
                )
            }
            Text(
                text = "ko~ko~da~yo~",
                color = Color(0xff666666),
                modifier = Modifier.background(Color(0x00ffcc)),
                style = TextStyle(
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Preview("column")
@Composable
fun ColumnLayout() {
    Column(
        modifier = Modifier
            .background(Color(0xFF063142))
            .padding(16.dp)
            .size(150.dp)
    ) {
        Text(
            text = "1", modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(0xFFFFFF00))
        )
        Text(
            text = "", modifier = Modifier
                .fillMaxWidth()
                .weight(1f, true)
                .background(Color(0xFF4385F4))
        )
        Text(
            text = "", modifier = Modifier
                .fillMaxWidth()
                .weight(1f, true)
                .background(Color(0xFF3CDB85))
        )
    }
}

@Preview("row")
@Composable
fun RowLayout() {
    Row(
        modifier = Modifier
            .size(200.dp)
            .background(Color(0xFF063142))
    ) {
        Text(
            text = "", modifier = Modifier
                .fillMaxHeight()
                .width(50.dp)
                .background(Color(0xFFFFFF00))
        )
        Text(
            text = "", modifier = Modifier
                .fillMaxHeight()
                .weight(1f, true)
                .background(Color(0xFF4385F4))
        )
        Text(
            text = "", modifier = Modifier
                .fillMaxHeight()
                .width(50.dp)
                .background(Color(0xFF3CDB85))
        )
    }
}

@Preview("box")
@Composable
fun BoxLayout() {
    Box(modifier = Modifier.size(200.dp)) {
        Text(
            text = "测试2", modifier = Modifier
                .background(Color(0xFF063142))
                .fillMaxSize()
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "测试",
                modifier = Modifier
                    .size(
                        100.dp, 100.dp
                    )
                    .background(Color.Yellow)
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactListItem(
    index: Int,onLongClick: ((Int) -> Unit)?,
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
            }, onDoubleClick = {
                Toast
                    .makeText(context, "双击操作", Toast.LENGTH_SHORT)
                    .show()
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
            Text(text = contact.userName, fontSize = 16.sp)
            Text(text = contact.email.value, fontSize = 10.sp, color = Color(0x52030303))
        }
    }

}

@Preview
@Composable
fun ContactList() {
    val dataSource = mockData()
    SimpleGalleryTheme() {
        LazyColumn() {
            dataSource.forEachIndexed { index, contact ->
                item {
                    ContactListItem(index, null,contact)
                }
            }
        }
    }
}

fun mockData(): List<Contact> {
    val list = mutableListOf<Contact>()
    list.add(Contact(1, Color.Cyan, "A", mutableStateOf("a@163")))
    list.add(Contact(2, Color.Black, "B", mutableStateOf("b@163")))
    list.add(Contact(3, Color.Cyan, "C", mutableStateOf("c@163")))
    list.add(Contact(4, Color.Cyan, "D", mutableStateOf("d@163")))
    list.add(Contact(5, Color.Cyan, "E", mutableStateOf("e@163")))
    return list
}

class ContactDefault : PreviewParameterProvider<Contact> {
    override val values = mockData().asSequence()
}

/*@Preview("state_manager")
@Composable
fun StateManagerView() {
    var name by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Hello")
        OutlinedTextField(value = name,
            onValueChange = { name = it },
            label = { Text(text = "name") }
        )
    }
}*/

/*@ExperimentalComposeUiApi
@SuppressLint("UnrememberedMutableState")
@Preview("drawView2")
@Composable
fun DrawView2() {
    var pathSave: SnapshotStateList<Pair<Boolean, Pair<Float, Float>>?> = mutableStateListOf(null)
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            pathSave.add(Pair(true, Pair(it.x, it.y)))
                        }
                        MotionEvent.ACTION_MOVE -> {
                            pathSave.add(Pair(false, Pair(it.x, it.y)))
                        }
                        MotionEvent.ACTION_UP -> {
                            //pathSave.add(Pair(false, Pair(it.x, it.y)))
                        }
                        else -> false
                    }
                    true
                }
        ) {
            drawPath(
                path = pathSave.toPath(),
                color = Color.Red,
                alpha = 0.5f,
                style = Stroke(4.dp.toPx())
            )
        }
        Text(
            modifier = Modifier
                .padding(20.dp)
                .background(Color(0xFF00ffff), RoundedCornerShape(12.dp))
                .padding(10.dp)
                .clickable {
                    pathSave.retract()
                },
            text = "清除"
        )
    }
}
private fun SnapshotStateList<Pair<Boolean, Pair<Float, Float>>?>.retract():
        SnapshotStateList<Pair<Boolean, Pair<Float, Float>>?> {
    var lastStart = 0;
    forEachIndexed { index, item ->
        if (item != null && item.first) {
            lastStart = index
        }
    }
    if (lastStart != 0) {
        removeRange(lastStart, size)
        removeLast()
    } else {
        clear()
    }
    return this
}
fun SnapshotStateList<Pair<Boolean, Pair<Float, Float>>?>.toPath(): Path {
    val path = Path()
    forEach {
        if (it != null) {
            if (it.first) {
                path.moveTo(it.second.first, it.second.second)
            } else {
                path.lineTo(it.second.first, it.second.second)
            }
        }
    }

    return path
}*/

@Preview("levelView")
@Composable
fun LevelView(singleAngle: Float = 30F, levelSize: Int = 8, angleValue: Float = 3F) {
    val size = 300.dp
    val lineSize = 10.dp
    val ration = ration(levelSize * singleAngle)
    val slipSize = 360 / singleAngle / 2
    val slipWidth = 5.dp
    Canvas(
        modifier = Modifier
            .size(size)
    ) {
        drawIntoCanvas { canvas ->
            val lineWidth = lineSize.toPx()
            rotate(ration, center) {
                canvas.saveLayer(
                    Rect(
                        Offset.Zero,
                        Size(size.toPx(), size.toPx())
                    ),
                    Paint()
                )
                drawArc(
                    color = Color.Yellow,
                    startAngle = 0F, sweepAngle = levelSize * singleAngle,
                    useCenter = false,
                    topLeft = Offset(lineWidth / 2, lineWidth / 2),
                    size = Size(
                        size.toPx() - lineWidth,
                        size.toPx() - lineWidth,
                    ),
                    alpha = 1.0f,
                    style = Stroke(lineWidth),
                )
                drawArc(
                    color = Color.Red,
                    startAngle = 0F, sweepAngle = angleValue,
                    useCenter = false,
                    topLeft = Offset(lineWidth / 2, lineWidth / 2),
                    size = Size(
                        size.toPx() - lineWidth,
                        size.toPx() - lineWidth,
                    ),
                    alpha = 1.0f,
                    style = Stroke(lineWidth),
                )
                //
                for (index in 0..slipSize.toInt()) {
                    rotate(index * singleAngle, center) {
                        drawLine(
                            Color.Yellow,
                            Offset(0f, size.toPx() / 2),
                            Offset(size.toPx(), size.toPx() / 2),
                            slipWidth.toPx(),
                            blendMode = BlendMode.DstOut
                        )
                    }
                }
                canvas.restore()
            }
        }
    }
}

fun ration(maxAngle: Float): Float {
    return if (maxAngle <= 90f) {
        270F - (maxAngle / 2)
    } else if (maxAngle > 90F && maxAngle <= 180F) {
        180F - (maxAngle / 2)
    } else if (maxAngle > 180F && maxAngle <= 270F) {
        180F - (maxAngle + 180 - 360) / 2
    } else {
        90F + (450F - (maxAngle + 90)) / 2
    }
}
