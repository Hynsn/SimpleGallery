package com.hynson.compose

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.hynson.compose.Utils.random

/**
 * Author: Hynsonhou
 * Date: 2022/10/21 11:40
 * Description:
 * History:
 * <author>  <time>     <version> <desc>
 * Hynsonhou  2022/10/21   1.0       首次创建
 */
object Utils {

    // 注意Color value class类型，更高效
    fun Color.random(): Color {
        val random = java.util.Random()
        val r = random.nextInt(256)
        val g = random.nextInt(256)
        val b = random.nextInt(256)
        return Color(r, g, b)
    }

    fun random(): String {
        return (Math.random() * 26 + 65).toInt().toChar().toString()
    }
}