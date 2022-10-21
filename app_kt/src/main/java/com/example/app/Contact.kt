package com.example.app

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow

data class Contact(
    val type:Int,
    val userIcon: Color,
    val userName:String,
    var email: MutableState<String>
)
