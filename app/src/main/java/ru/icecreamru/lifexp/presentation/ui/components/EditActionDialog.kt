package ru.icecreamru.lifexp.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.icecreamru.lifexp.R
import ru.icecreamru.lifexp.data.model.Action

@Composable
fun EditActionDialog(
    action: Action,
    onDismissRequest: () -> Unit,
    onConfirm: (Action) -> Unit,
    onDelete: () -> Unit
) {
    var name by remember { mutableStateOf(action.name) }
    var experiencePoints by remember { mutableStateOf(action.experiencePoints.toString()) }
    var isPositive by remember { mutableStateOf(action.isPositive) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(id = R.string.edit_action)) },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                    label = { Text(stringResource(id = R.string.name)) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = experiencePoints,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty() || newValue.matches(Regex("^\\d+$"))) {
                            experiencePoints = newValue
                        }
                    },
                    label = { Text(stringResource(R.string.sum)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(R.string.action_type))
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = isPositive,
                        onCheckedChange = { isPositive = it }
                    )
                    Text(
                        text = if (isPositive) stringResource(id = R.string.positive) else stringResource(id = R.string.negative),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { showDeleteConfirmation = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(stringResource(id = R.string.delete_action))
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val experiencePointsInt = experiencePoints.toIntOrNull()
                if (name.isNotBlank() && experiencePointsInt != null && experiencePointsInt > 0) {
                    onConfirm(action.copy(name = name, experiencePoints = experiencePointsInt, isPositive = isPositive))
                }
            }) {
                Text(stringResource(id = R.string.save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(id = R.string.cancel))
            }
        }
    )

    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = { Text(stringResource(R.string.delete_confirmation_title)) },
            text = { Text(stringResource(R.string.delete_confirmation_text)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteConfirmation = false
                        onDelete()
                    }
                ) {
                    Text(stringResource(id = R.string.delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmation = false }) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        )
    }
}