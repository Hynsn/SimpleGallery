package com.example.app

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Author: Hynsonhou
 * Date: 2022/11/14 16:08
 * Description: 常见控件使用
 * History:
 * <author>  <time>     <version> <desc>
 * Hynsonhou  2022/11/14   1.0       首次创建
 */
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

@Preview("state_manager")
@Composable
fun StateManagerView() {
    var name by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Hello")
        OutlinedTextField(value = name, onValueChange = {
            Log.i("TAG", it)
            name = it
        }, label = { Text(text = name) })
    }
}

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

@ExperimentalComposeUiApi
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
}