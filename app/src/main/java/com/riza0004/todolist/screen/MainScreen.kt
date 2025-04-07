package com.riza0004.todolist.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.riza0004.todolist.R
import com.riza0004.todolist.dataClass.ToDoListDataClass
import com.riza0004.todolist.navigation.Screen
import com.riza0004.todolist.ui.theme.ToDoListTheme
import com.riza0004.todolist.viewModel.ToDoListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    toDoListViewModel: ToDoListViewModel
    ){
    var expanded by remember { mutableStateOf(false) }
    Scaffold(
        topBar = { TopAppBar(
            title = { Text(stringResource(R.string.app_name))},
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
            actions = {
                Box {
                    IconButton(
                        onClick = {
                            expanded = !expanded
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.threeDotButtonDesc)
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {expanded = false}
                        ) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.addButton)) },
                                onClick = {
                                    expanded = false
                                    navController.navigate(Screen.AddToDoList.route)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.aboutAppButton)) },
                                onClick = {
                                    expanded = false
                                    navController.navigate(Screen.AboutThisApp.route)
                                }
                            )
                        }
                    }
                }
            }
        ) }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding), toDoListViewModel)
    }
}

@Composable
fun ScreenContent(
    modifier: Modifier,
    toDoListViewModel: ToDoListViewModel
    ){
    val toDoList by toDoListViewModel.toDoList.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
        )
    {
        if(toDoList.isEmpty()){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.dataIsEmptyTitle),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.dataIsEmptyBody),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }
        else{
            Column{
                val notDoneInTotal = toDoList.count { !it.isDone }
                val doneInTotal = toDoList.size - notDoneInTotal
                val isDoneExpanded = remember { mutableStateOf(false) }
                val isNotDoneExpanded = remember { mutableStateOf(true) }
                val sortedList = toDoList.sortedWith(comparator = compareBy { it.priority })
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = stringResource(R.string.titleNotDoneMainScreen, notDoneInTotal),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = { isNotDoneExpanded.value = !isNotDoneExpanded.value }
                    ) {
                        Icon(
                            imageVector = (if(isNotDoneExpanded.value){Icons.Default.KeyboardArrowDown} else{Icons.AutoMirrored.Default.KeyboardArrowRight}),
                            contentDescription = ""
                        )
                    }
                }
                LazyColumn(
                    modifier = (if(isNotDoneExpanded.value){Modifier.wrapContentHeight()} else{Modifier.height(0.dp)}),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    items(sortedList){
                            item->
                        if(!item.isDone){
                            ToDoListCard(item, toDoListViewModel = toDoListViewModel)
                        }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = stringResource(R.string.titleDoneMainScreen, doneInTotal),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = { isDoneExpanded.value = !isDoneExpanded.value }
                    ) {
                        Icon(
                            imageVector = (if(isDoneExpanded.value){Icons.Default.KeyboardArrowDown} else{Icons.AutoMirrored.Default.KeyboardArrowRight}),
                            contentDescription = ""
                        )
                    }
                }
                LazyColumn(
                    modifier = (if(isDoneExpanded.value){Modifier.wrapContentHeight()} else{Modifier.height(0.dp)}),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    items(sortedList){
                            item->
                        if(item.isDone){
                            ToDoListCard(item, toDoListViewModel = toDoListViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ToDoListCard(toDoListDataClass: ToDoListDataClass, toDoListViewModel:ToDoListViewModel){
    var expanded by remember { mutableStateOf(false) }
    if(toDoListDataClass.priority > 2){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            shape = RoundedCornerShape(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Gray,
                contentColor = Color.Black
            )
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ){
                Text(
                    text = stringResource(R.string.dataNotFound)
                )
            }
            }
    }
    else{
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            shape = RoundedCornerShape(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = when(toDoListDataClass.priority){
                    0-> {Color.Red}
                    1-> {Color.Yellow}
                    else-> {Color.Green}
                },
                contentColor = Color.Black
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text =  toDoListDataClass.title + " - " +
                                when (toDoListDataClass.priority) {
                                    0 -> {
                                        stringResource(R.string.firstPriority)
                                    }
                                    1 -> {
                                        stringResource(R.string.secondPriority)
                                    }
                                    else -> {
                                        stringResource(R.string.thirdPriority)
                                    }
                                },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = {expanded = !expanded},
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(R.string.threeDotButtonDesc)
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {expanded = false}
                        ) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.deleteButton)) },
                                onClick = {
                                    expanded = false
                                    toDoListViewModel.destroyToDOList(toDoListDataClass)
                                }
                            )
                            DropdownMenuItem(
                                text = {if(toDoListDataClass.isDone){
                                    Text(stringResource(R.string.notDoneButton))
                                }
                                else{
                                    Text(stringResource(R.string.doneButton))
                                }},
                                onClick = {
                                    expanded = false
                                    toDoListViewModel.changeDone(toDoListDataClass)
                                }
                            )
                        }
                    }
                }
                Text(
                    text = toDoListDataClass.content,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    ToDoListTheme {
        MainScreen(navController = rememberNavController(), ToDoListViewModel())
    }
}