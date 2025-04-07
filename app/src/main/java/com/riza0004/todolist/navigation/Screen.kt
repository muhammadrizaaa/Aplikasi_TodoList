package com.riza0004.todolist.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object AddToDoList: Screen("addToDoListScreen")
    data object AboutThisApp: Screen("aboutThisApp")
}