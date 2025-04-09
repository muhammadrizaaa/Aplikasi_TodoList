package com.riza0004.todolist.components

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.riza0004.todolist.R
import com.riza0004.todolist.ui.theme.ToDoListTheme

@Composable
fun SimpleDialog(
    onDismiss: ()->Unit,
    onConfirmation: ()->Unit,
    dialogTitle: String,
    dialogCOntent:String,
    icon: ImageVector,
    contentDescription: String
)
{
    AlertDialog(
        icon = {Icon(icon, contentDescription = contentDescription)},
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogCOntent) },
        onDismissRequest = {
            onDismiss()
                           },
        confirmButton = {
            TextButton(
                onClick = onConfirmation,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            )
            {
                Text(text = stringResource(R.string.yes))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(text = stringResource(R.string.no))
            }
        },
        titleContentColor = MaterialTheme.colorScheme.primary,
        textContentColor = MaterialTheme.colorScheme.primary,
        iconContentColor = MaterialTheme.colorScheme.primary,
    )
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DialogPreview() {
    ToDoListTheme {
        SimpleDialog({}, {}, stringResource(R.string.titleForDialogDelete), stringResource(R.string.contentForDialogDelete), Icons.Default.Delete, stringResource(R.string.deleteIconDesc))
    }
}