package com.hynson.compose

data class ContentItem(
    val name: String,
    var colors: Pair<Int, Int> = ColorUtil.getColorPair(),
    val action: () -> Unit,
)
