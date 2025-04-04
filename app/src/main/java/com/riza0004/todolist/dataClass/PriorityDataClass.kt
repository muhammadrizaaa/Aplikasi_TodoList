package com.riza0004.todolist.dataClass

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.painter.Painter

data class PriorityDataClass(
    val priorityStr:String,
    val priorityInt:Int,
    @DrawableRes val imageResId: Int,
    val img: Painter
)
