package com.hynson.compose.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.util.Date
import java.util.LinkedList
import kotlin.math.roundToInt

/**
 * Author: Hynsonhou
 * Date: 2023/12/29 14:28
 * Description: 滚轮控件
 * History:
 * <author> <time> <version> <desc>
 * Hynsonhou 2023/12/29 1.0 首次创建
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> WheelPicker(
    data: List<T>,
    selectIndex: Int,
    visibleCount: Int,
    modifier: Modifier = Modifier,
    onSelect: (index: Int, item: T) -> Unit,
    content: @Composable (item: T) -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier,
        propagateMinConstraints = true
    ) {
        val density = LocalDensity.current
        val size = data.size
        val count = data.size
        val pickerHeight = maxHeight
        val pickerHeightPx = density.run { pickerHeight.toPx() }
        val pickerCenterLinePx = pickerHeightPx / 2
        val itemHeight = pickerHeight / visibleCount
        val itemHeightPx = pickerHeightPx / visibleCount
        val startIndex = count / 2
        val listState = rememberLazyListState(
            initialFirstVisibleItemIndex = startIndex - startIndex.floorMod(size) + selectIndex,
            initialFirstVisibleItemScrollOffset = ((itemHeightPx - pickerHeightPx) / 2).roundToInt(),
        )
        val layoutInfo by remember { derivedStateOf { listState.layoutInfo } }
        LazyColumn(
            modifier = Modifier,
            state = listState,
            flingBehavior = rememberSnapFlingBehavior(listState),
        ) {
            for (i in 1..visibleCount / 2) {
                item {
                    Surface(modifier = Modifier.height(itemHeight)) {}
                }
            }
            items(count) { index ->
                val currIndex = (index - startIndex).floorMod(size)
                val item = layoutInfo.visibleItemsInfo.find { it.index == index }
                var percent = 1f
                if (item != null) {
                    val itemCenterY = item.offset + item.size / 2
                    percent = if (itemCenterY < pickerCenterLinePx) {
                        itemCenterY / pickerCenterLinePx
                    } else {
                        1 - (itemCenterY - pickerCenterLinePx) / pickerCenterLinePx
                    }
                    if (!listState.isScrollInProgress
                        && item.offset < pickerCenterLinePx
                        && item.offset + item.size > pickerCenterLinePx
                    ) {
                        onSelect(currIndex, data[currIndex])
                    }
                }
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            alpha = 0.75f + 0.25f * percent
                            scaleX = 0.75f + 0.25f * percent
                            scaleY = 0.75f + 0.25f * percent
                            rotationX = (1 + (0.75f + 0.25f * percent)) * 180
                        }
                        .fillMaxWidth()
                        .height(itemHeight),
                    contentAlignment = Alignment.Center,
                ) {
                    content(data[currIndex])
                }
            }
            for (i in 1..visibleCount / 2) {
                item {
                    Surface(modifier = Modifier.height(itemHeight)) {}
                }
            }
        }
    }
}

private fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> ListNumberPicker(
    data: List<T>,
    selectIndex: Int,
    visibleCount: Int,
    modifier: Modifier = Modifier,
    onSelect: (index: Int, item: T) -> Unit,
    content: @Composable (item: T) -> Unit,
) {
    BoxWithConstraints(modifier = modifier, propagateMinConstraints = true) {
        val pickerHeight = maxHeight
        val size = data.size
        val itemHeight = pickerHeight / visibleCount
        val listState = rememberLazyListState(
            initialFirstVisibleItemIndex = selectIndex
        )
        val firstVisibleItemIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
        LazyColumn(
            modifier = Modifier,
            state = listState,
            flingBehavior = rememberSnapFlingBehavior(listState),
        ) {
            for (i in 1..visibleCount / 2) {
                item {
                    Surface(modifier = Modifier.height(itemHeight)) {}
                }
            }
            items(size) { index ->
                //预防滑动的时候出现数组越界
                if (firstVisibleItemIndex >= size) {
                    onSelect(size - 1, data[size - 1])
                } else {
                    onSelect(firstVisibleItemIndex, data[firstVisibleItemIndex])
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeight)
                        .semantics(mergeDescendants = true) {

                        },
                    contentAlignment = Alignment.Center,
                ) {
                    content(data[index])
                }
            }
            for (i in 1..visibleCount / 2) {
                item {
                    Surface(modifier = Modifier.height(itemHeight)) {}
                }
            }
        }

    }
}

@Composable
fun DatePickerColumn(
    //	列表
    pairList: List<Pair<Int, String>>,
    itemHeight: Dp,
    itemWidth: Dp? = null,
    valueState: MutableState<Int>,
    focusColor: Color = MaterialTheme.colors.primary,
    unfocusColor: Color = Color(0xFFC5C7CF)
) {
    var isInit = false

    val dataPickerCoroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var value by valueState
    LazyColumn(
        state = listState,
        modifier = Modifier
            .height(itemHeight * 6)
            .padding(top = itemHeight / 2, bottom = itemHeight / 2)
    ) {
        item {
            Surface(Modifier.height(itemHeight)) {}
        }
        item {
            Surface(Modifier.height(itemHeight)) {}
        }
        itemsIndexed(items = pairList, key = { index, pair -> pair.first }) { index, pair ->

            val widthModifier = itemWidth?.let { Modifier.width(itemWidth) } ?: Modifier
            Box(
                modifier = Modifier
                    .height(itemHeight)
                    .then(widthModifier)
                    .clickable {
                        dataPickerCoroutineScope.launch {
                            listState.animateScrollToItem(index = index)
                        }
                    }
                    .padding(start = 5.dp, end = 5.dp), Alignment.Center
            ) {
                Text(
                    text = pair.second, color =
                    if (listState.firstVisibleItemIndex == index) focusColor
                    else unfocusColor
                )
            }
        }
        item {
            Surface(Modifier.height(itemHeight)) {}
        }
        item {
            Surface(Modifier.height(itemHeight)) {}
        }
    }


    /**
     * Jetpack Compose LazyColumn的滑动开始、结束及进行中事件
     * 参考文章 https://blog.csdn.net/asd912756674/article/details/122544808
     */
    if (listState.isScrollInProgress) {

        LaunchedEffect(Unit) {
            //只会调用一次，相当于滚动开始
        }
        //当state处于滚动时，preScrollStartOffset会被初始化并记忆,不会再被更改
        val preScrollStartOffset by remember { mutableStateOf(listState.firstVisibleItemScrollOffset) }
        val preItemIndex by remember { mutableStateOf(listState.firstVisibleItemIndex) }
        val isScrollDown = if (listState.firstVisibleItemIndex > preItemIndex) {
            //第一个可见item的index大于开始滚动时第一个可见item的index，说明往下滚动了
            true
        } else if (listState.firstVisibleItemIndex < preItemIndex) {
            //第一个可见item的index小于开始滚动时第一个可见item的index，说明往上滚动了
            false
        } else {
            //第一个可见item的index等于开始滚动时第一个可见item的index,对比item offset
            listState.firstVisibleItemScrollOffset > preScrollStartOffset
        }

        DisposableEffect(Unit) {
            onDispose {
                //	滑动结束时给状态赋值，并自动对齐
                value = pairList[listState.firstVisibleItemIndex].first
                dataPickerCoroutineScope.launch {
                    listState.animateScrollToItem(listState.firstVisibleItemIndex)
                }
            }
        }
    }

    //  选择初始值
    LaunchedEffect(Unit) {

        var initIndex = 0

        for (index in pairList.indices) {
            if (value == pairList[index].first) {
                initIndex = index
                break
            }
        }
        dataPickerCoroutineScope.launch {
            listState.animateScrollToItem(initIndex)
        }
    }
}

@Composable
fun DataTimePicker(
    date: Date = Date()
) {

    val itemHeight = 50.dp

    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        Alignment.Center
    ) {
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface),
            Arrangement.SpaceEvenly,
            Alignment.CenterVertically
        ) {
            val year = date.getYearr()
            val selectYear = rememberSaveable { mutableStateOf(year) }
            val years = LinkedList<Pair<Int, String>>().apply {
                for (i in year downTo 1980) {
                    add(Pair(i, "${i}年"))
                }
            }
            DatePickerColumn(years, itemHeight, 70.dp, selectYear)

            //  月份
            val month = date.getMonthh()
            val selectMonth = rememberSaveable { mutableStateOf(month) }
            val months = ArrayList<Pair<Int, String>>(12).apply {
                for (i in 1..12) {
                    add(Pair(i, "${i}月"))
                }
            }
            DatePickerColumn(months, itemHeight, 50.dp, selectMonth)

            //  月份的天数
            val dayOfMon = date.getDayOfMonth()
            val selectDay = remember { mutableStateOf(dayOfMon) }
            val dayCountOfMonth = DateUtil.getDayCountOfMonth(selectYear.value, selectMonth.value)

            //  提前定义好
            val day31 = ArrayList<Pair<Int, String>>().apply {
                for (i in 1..31)
                    add(Pair(i, "${i}日"))
            }
            val day30 = ArrayList<Pair<Int, String>>().apply {
                for (i in 1..30)
                    add(Pair(i, "${i}日"))
            }
            val day29 = ArrayList<Pair<Int, String>>().apply {
                for (i in 1..29)
                    add(Pair(i, "${i}日"))
            }
            val day28 = ArrayList<Pair<Int, String>>().apply {
                for (i in 1..28)
                    add(Pair(i, "${i}日"))
            }

            //  快速切换
            val dayOfMonList = when (dayCountOfMonth) {
                28 -> day28
                29 -> day29
                30 -> day30
                else -> day31
            }

            DatePickerColumn(
                pairList = dayOfMonList,
                itemHeight = itemHeight,
                valueState = selectDay
            )

            //  小时
            val hour = date.getHour()
            val selectHour = remember { mutableStateOf(hour) }
            val hours = ArrayList<Pair<Int, String>>(24).apply {
                for (i in 0..23) {
                    add(Pair(i, "${i}时"))
                }
            }
            DatePickerColumn(hours, itemHeight, 50.dp, selectHour)

            //  分
            val minute = date.getMinute()
            val selectMinute = remember { mutableStateOf(minute) }
            val minutes = ArrayList<Pair<Int, String>>(60).apply {
                for (i in 0..59) {
                    add(Pair(i, "${i}分"))
                }
            }
            DatePickerColumn(minutes, itemHeight, 50.dp, selectMinute)

            //  秒
            val second = date.getSecond()
            val selectSecond = remember { mutableStateOf(second) }
            val seconds = ArrayList<Pair<Int, String>>(60).apply {
                for (i in 0..59) {
                    add(Pair(i, "${i}秒"))
                }
            }
            DatePickerColumn(seconds, itemHeight, 50.dp, selectSecond)
        }

        //  放在后面使得不会被遮住
        Column {
            Divider(
                Modifier.padding(
                    start = 15.dp,
                    end = 15.dp,
                    bottom = itemHeight
                ),
                thickness = 1.dp
            )
            Divider(
                Modifier.padding(
                    start = 15.dp,
                    end = 15.dp
                ),
                thickness = 1.dp
            )
        }
    }
}


