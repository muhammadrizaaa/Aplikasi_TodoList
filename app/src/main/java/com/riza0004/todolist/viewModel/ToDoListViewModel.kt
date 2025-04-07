package com.riza0004.todolist.viewModel

import androidx.lifecycle.ViewModel
import com.riza0004.todolist.dataClass.ToDoListDataClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ToDoListViewModel:ViewModel() {
    private val _toDoList = MutableStateFlow<List<ToDoListDataClass>>(emptyList())
    val toDoList = _toDoList.asStateFlow()

    fun addToDoList(title:String, content:String, priority:Int, isDone:Boolean){

        _toDoList.value += ToDoListDataClass(_toDoList.value.size + 1, title, content, priority, isDone)
    }
    fun destroyToDOList(item: ToDoListDataClass){
        _toDoList.value -= item
    }
    fun changeDone(item: ToDoListDataClass){
        _toDoList.value = _toDoList.value.map {
            if (it.id == item.id) it.copy(isDone = !item.isDone) else it
        }
    }
}