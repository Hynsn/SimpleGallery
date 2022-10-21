package com.example.app

import androidx.compose.ui.graphics.Color

/**
 * Author: Hynsonhou
 * Date: 2022/10/21 11:40
 * Description:
 * History:
 * <author>  <time>     <version> <desc>
 * Hynsonhou  2022/10/21   1.0       首次创建
 */
object Utils {
    fun color(): Color {
        val random = java.util.Random()
        val r = random.nextInt(256)
        val g = random.nextInt(256)
        val b = random.nextInt(256)

        return Color(r, g, b)
    }

    fun c(): Char {
        return (Math.random() * 26 + 65).toInt().toChar()
    }
}