package ru.icecreamru.lifexp.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.icecreamru.lifexp.data.model.Action
import ru.icecreamru.lifexp.presentation.ui.theme.LifeXPTheme
import ru.icecreamru.lifexp.presentation.viewmodels.MainViewModel
import ru.icecreamru.lifexp.presentation.viewmodels.UiState
import ru.icecreamru.lifexp.util.Constants
import ru.icecreamru.lifexp.util.Constants.MAX_EXPERIENCE

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Success -> SuccessScreen(state.actions, state.experience, viewModel::performAction)
        is UiState.Error -> ErrorScreen(state.message)
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
    }
}

@Composable
fun SuccessScreen(actions: List<Action>, experience: Int, onActionClick: (Action) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Current Experience: $experience",
            style = MaterialTheme.typography.labelLarge, //h6
            modifier = Modifier.padding(bottom = 16.dp)
        )
        ExperienceBar(experience = experience)
        Spacer(modifier = Modifier.height(16.dp))
        ActionList(actions = actions, onActionClick = onActionClick)
    }
}

@Composable
fun ActionList(actions: List<Action>, onActionClick: (Action) -> Unit) {
    LazyColumn {
        items(actions) { action ->
            ActionItem(action = action, onClick = { onActionClick(action) })
        }
    }
}

@Composable
fun ActionItem(action: Action, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = action.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${if (action.isPositive) "+" else "-"}${action.experiencePoints}",
                color = if (action.isPositive) Color.Green else Color.Red
            )
        }
    }
}

@Composable
fun ExperienceBar(experience: Int) {
    val progress = experience.toFloat() / MAX_EXPERIENCE
    LinearProgressIndicator(
        progress = progress,
        modifier = Modifier.fillMaxWidth().height(16.dp)
    )
    Text(text = "Experience: $experience / $MAX_EXPERIENCE")
}

@Preview(showBackground = true)
@Composable
fun MainScreenLoadingPreview() {
    LifeXPTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenErrorPreview() {
    LifeXPTheme {
        ErrorScreen("An error occurred while loading data")
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenSuccessPreview() {
    LifeXPTheme {
        val sampleActions = listOf(
            Action(1, "Training", Constants.TRAINING_EXPERIENCE, true),
            Action(2, "Walking", Constants.WALKING_EXPERIENCE, true),
            Action(3, "Smoking Hookah", Constants.HOOKAH_EXPERIENCE, false)
        )
        SuccessScreen(
            actions = sampleActions,
            experience = 250,
            onActionClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenDarkThemePreview() {
    LifeXPTheme(darkTheme = true) {
        val sampleActions = listOf(
            Action(1, "Training", Constants.TRAINING_EXPERIENCE, true),
            Action(2, "Walking", Constants.WALKING_EXPERIENCE, true),
            Action(3, "Smoking Hookah", Constants.HOOKAH_EXPERIENCE, false)
        )
        SuccessScreen(
            actions = sampleActions,
            experience = 250,
            onActionClick = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun MainScreenPhonePreview() {
    LifeXPTheme {
        val sampleActions = listOf(
            Action(1, "Training", Constants.TRAINING_EXPERIENCE, true),
            Action(2, "Walking", Constants.WALKING_EXPERIENCE, true),
            Action(3, "Smoking Hookah", Constants.HOOKAH_EXPERIENCE, false)
        )
        SuccessScreen(
            actions = sampleActions,
            experience = 250,
            onActionClick = {}
        )
    }
}