package com.riza0004.todolist.dataClass

data class ToDoListDataClass(
    val id: Int,
    val title:String,
    val content:String,
    val priority:Int,
    var isDone:Boolean
)
