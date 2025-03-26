package com.riza0004.todolist.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.riza0004.todolist.R
import com.riza0004.todolist.ui.theme.ToDoListTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Scaffold(
        floatingActionButton = {
            Box(
                contentAlignment = Alignment.Center
            ) {
                FloatingActionButton(
                    onClick = {expanded = !expanded}
                ) {
                    Icon(Icons.Default.Add, stringResource(R.string.addButton), tint = MaterialTheme.colorScheme.primary)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {expanded = false}
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Add") },
                        onClick = {
                            expanded = false
                            Toast.makeText(context, "Add Clicked", Toast.LENGTH_SHORT).show()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("About") },
                        onClick = {
                            expanded = false
                            Toast.makeText(context, "About Clicked", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        },
        topBar = { TopAppBar(
            title = { Text(stringResource(R.string.app_name))},
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
        ) }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun ScreenContent(modifier: Modifier){
    Box(
        modifier = modifier.fillMaxSize()
            .padding(8.dp))
    {
        Column(
            modifier = Modifier.padding(4.dp).fillMaxSize().verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                shape = RoundedCornerShape(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Absolute.SpaceBetween
                    ) {
                        Text(
                            text = "Judul",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Priority",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        text = "IsiIsiIsiIsiIsiIsiIsiIsiIsiIsiIsiIsiIsiIsiIsiIsi",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    ToDoListTheme {
        MainScreen()
    }
}