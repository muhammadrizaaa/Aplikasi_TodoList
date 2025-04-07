package com.riza0004.todolist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.riza0004.todolist.screen.AboutThisAppScreen
import com.riza0004.todolist.screen.AddToDoListFormScreen
import com.riza0004.todolist.screen.MainScreen
import com.riza0004.todolist.viewModel.ToDoListViewModel

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController(), toDoListViewModel: ToDoListViewModel){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route){
            MainScreen(navController, toDoListViewModel)
        }
        composable(route = Screen.AddToDoList.route){
            AddToDoListFormScreen(navController, toDoListViewModel)
        }
        composable(route = Screen.AboutThisApp.route){
            AboutThisAppScreen(navController)
        }
    }
}