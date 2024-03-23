package com.hynson.compose.widget

/**
 * Author: Hynsonhou
 * Date: 2024/3/21 16:59
 * Description: 小组件接收者
 * History:
 * <author> <time> <version> <desc>
 * Hynsonhou 2024/3/21 1.0 首次创建
 */
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class CounterWidgetReceiver : GlanceAppWidgetReceiver(){
    override val glanceAppWidget: GlanceAppWidget = CounterWidget()
}
