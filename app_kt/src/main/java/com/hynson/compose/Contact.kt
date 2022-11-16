package com.hynson.compose

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

data class Contact(
    val type:Int,
    val userIcon: Color,
    val userName:String,
    var counter: MutableState<Int>
)
