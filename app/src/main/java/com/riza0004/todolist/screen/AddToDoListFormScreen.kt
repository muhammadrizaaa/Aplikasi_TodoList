package com.riza0004.todolist.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.riza0004.todolist.R
import com.riza0004.todolist.dataClass.PriorityDataClass
import com.riza0004.todolist.navigation.Screen
import com.riza0004.todolist.ui.theme.ToDoListTheme
import com.riza0004.todolist.viewModel.ToDoListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToDoListFormScreen(navController: NavHostController, toDoListViewModel: ToDoListViewModel){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.addToDoListFromTitle)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.backButtonDesc)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        AddToDoListFormContent(Modifier.padding(innerPadding), toDoListViewModel, navController = navController)
    }
}
@Composable
fun AddToDoListFormContent(modifier: Modifier, toDoListViewModel: ToDoListViewModel, navController: NavHostController){
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val radioOpt:List<PriorityDataClass> = listOf(
        PriorityDataClass(priorityStr = stringResource(R.string.firstPriority), priorityInt = 0, imageResId = R.drawable.urgent, img = painterResource(R.drawable.urgent)),
        PriorityDataClass(priorityStr = stringResource(R.string.secondPriority), priorityInt = 1, imageResId = R.drawable.moderate, img = painterResource(R.drawable.moderate)),
        PriorityDataClass(priorityStr = stringResource(R.string.thirdPriority), priorityInt = 2, imageResId = R.drawable.unimportant, img = painterResource(R.drawable.unimportant))
    )
    var priority by remember { mutableIntStateOf(0) }
    var titleErr by remember{ mutableStateOf(false) }
    var contentErr by remember{ mutableStateOf(false) }
    Box(modifier
        .fillMaxSize()
        .padding(8.dp)){
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {title = it},
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.LabelTextFieldTitle)) },
                isError = titleErr,
                supportingText = { ErrorMessage(titleErr) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            OutlinedTextField(
                value = content,
                onValueChange = {content = it},
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.LabelTextFieldContent)) },
                isError = contentErr,
                supportingText = { ErrorMessage(contentErr) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
            )

            radioOpt.forEach { priorityData ->
                PriorityOpt(
                    isSelected = priority == priorityData.priorityInt,
                    modifier = Modifier.selectable(
                        selected = priority == priorityData.priorityInt,
                        onClick = {priority = priorityData.priorityInt},
                        role = Role.RadioButton
                    ),
                    img = priorityData.img,
                    label = priorityData.priorityStr
                )
            }

            Button(
                onClick = {
                    titleErr = title.isBlank()
                    contentErr = content.isBlank()
                    if(!titleErr && !contentErr){
                        toDoListViewModel.addToDoList(title = title, content = content, priority = priority, isDone = false)
                        navController.navigate(Screen.Home.route)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text(
                    stringResource(R.string.submitButtonAddToDoList),
                    color = MaterialTheme.colorScheme.primary
                )
            }

        }
    }
}

@Composable
fun PriorityOpt(isSelected: Boolean, modifier: Modifier, img: Painter, label: String){
    Row(
        modifier = modifier.padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Column(
            modifier = Modifier
                .width(100.dp)
                .padding(start = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = img,
                contentDescription = stringResource(R.string.priorityContentDesc, label),
                modifier = Modifier.size(75.dp)
            )
            Text(
                text = label
                )
        }
    }
}

@Composable
fun ErrorMessage(isError: Boolean){
    if(isError){
        Text(
            text = stringResource(R.string.errorMessageAddToDoList),

        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AddToDoListFormPreview() {
    ToDoListTheme {
        AddToDoListFormScreen(rememberNavController(), ToDoListViewModel())
    }
}