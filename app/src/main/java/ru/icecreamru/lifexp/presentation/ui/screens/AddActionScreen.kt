package ru.icecreamru.lifexp.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.icecreamru.lifexp.presentation.ui.theme.LifeXPTheme
import ru.icecreamru.lifexp.presentation.viewmodels.AddActionViewModel

@Composable
fun AddActionScreen(viewModel: AddActionViewModel = hiltViewModel()) {
    EditScreen()
}

@Composable
fun EditScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        var title by rememberSaveable { mutableStateOf("") }
        var experienceCount by rememberSaveable { mutableStateOf(0) }

        TitleInputField(title = title, onValueChange = { newValue -> title = newValue })
        ExperienceInputField(experienceCount, modifier)
    }
}

@Composable
private fun ExperienceInputField(experienceCount: Int, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Кол-во опыта:")
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "+", modifier = Modifier.padding(end = 8.dp))
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),

            value = "1",
            onValueChange = {},
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            modifier = modifier
                .width(56.dp)
                .height(56.dp)
        )
    }
}

@Composable
private fun TitleInputField(title: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        label = { Text(text = "Название") },
        value = title,
        onValueChange = { value -> onValueChange(title) },
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
@Preview(showBackground = true)
fun EditScreenPreview() {
    LifeXPTheme {
        EditScreen()
    }
}