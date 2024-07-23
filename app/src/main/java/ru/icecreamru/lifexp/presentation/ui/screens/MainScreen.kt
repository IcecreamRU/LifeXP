package ru.icecreamru.lifexp.presentation.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.icecreamru.lifexp.R
import ru.icecreamru.lifexp.data.model.Action
import ru.icecreamru.lifexp.presentation.ui.components.AddActionDialog
import ru.icecreamru.lifexp.presentation.ui.components.EditActionDialog
import ru.icecreamru.lifexp.presentation.ui.components.MilestoneNotification
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
        is UiState.Success -> SuccessScreen(
            state.actions,
            state.experience,
            state.showMilestoneNotification,
            viewModel::performAction,
            viewModel::updateAction,
            viewModel::addNewAction,
            viewModel::deleteAction
        ) { viewModel.dismissMilestoneNotification() }

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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SuccessScreen(
    actions: List<Action>,
    experience: Int,
    showMilestoneNotification: Boolean,
    onActionClick: (Action) -> Unit,
    onUpdateAction: (Action) -> Unit,
    onAddAction: (String, Int, Boolean) -> Unit,
    onDeleteAction: (Int) -> Unit,
    onDismissMilestoneNotification: () -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var editableAction by remember { mutableStateOf<Action?>(null) }

    Scaffold(floatingActionButton = {
        // в разработке
        FloatingActionButton(
            onClick = {
                showAddDialog = true
            },
        ) {
            Icon(Icons.Filled.Add, stringResource(id = R.string.add_action))
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.current_experience, experience),
                style = MaterialTheme.typography.labelLarge, //h6
                modifier = Modifier.padding(bottom = 16.dp)
            )
            ExperienceBar(experience = experience)
            Spacer(modifier = Modifier.height(16.dp))
            ActionList(actions = actions, onActionClick = onActionClick, onEditClick = { action ->
                editableAction = action
            })
        }

        if (showAddDialog) {
            AddActionDialog(onDismissRequest = {
                showAddDialog = false
            }) { name, experiencePoints, isPositive ->
                onAddAction(name, experiencePoints, isPositive)
                showAddDialog = false
            }
        }

        editableAction?.let { action ->
            EditActionDialog(
                action = action,
                onDismissRequest = { editableAction = null },
                onConfirm = { confirmedAction ->
                    onUpdateAction(confirmedAction)
                    editableAction = null
                },
                onDelete = {
                    onDeleteAction(action.id)
                    editableAction = null
                }
            )
        }

        if (showMilestoneNotification) {
            MilestoneNotification(onDismiss = onDismissMilestoneNotification)
        }
    }
}

@Composable
fun ActionList(
    actions: List<Action>,
    onActionClick: (Action) -> Unit,
    onEditClick: (Action) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = actions,
            key = { it.id }
        ) { action ->
            AnimatedVisibility(
                visible = true,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                ActionItem(
                    action = action,
                    onClick = { onActionClick(action) },
                    onEditClick = { onEditClick(action) }
                )
            }
        }
    }
}

@Composable
fun ActionItem(
    action: Action,
    onClick: () -> Unit,
    onEditClick: () -> Unit
) {
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
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "${if (action.isPositive) "+" else "-"}${action.experiencePoints}",
                color = if (action.isPositive) Color.Green else Color.Red
            )
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(id = R.string.edit_action)
                )
            }
        }
    }
}

@Composable
fun ExperienceBar(experience: Int) {
    val animatedProgress by animateFloatAsState(
        targetValue = experience.toFloat() / MAX_EXPERIENCE,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    Column {
        LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(id = R.string.experience) + " $experience / $MAX_EXPERIENCE",
            style = MaterialTheme.typography.bodySmall
        )
//        Text(
//            text = stringResource(id = R.string.experience, experience, MAX_EXPERIENCE),
//            )
    }
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
            Action(
                name = stringResource(id = R.string.training),
                experiencePoints = Constants.TRAINING_EXPERIENCE,
                isPositive = true
            ),
            Action(
                name = stringResource(id = R.string.walking),
                experiencePoints = Constants.WALKING_EXPERIENCE,
                isPositive = true
            ),
            Action(
                name = stringResource(id = R.string.smoking_hookah),
                experiencePoints = Constants.HOOKAH_EXPERIENCE,
                isPositive = false
            )
        )
        SuccessScreen(
            actions = sampleActions,
            experience = 250,
            showMilestoneNotification = false,
            onActionClick = {},
            onAddAction = { _, _, _ -> },
            onUpdateAction = {},
            onDeleteAction = {},
            onDismissMilestoneNotification = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenDarkThemePreview() {
    LifeXPTheme(darkTheme = true) {
        val sampleActions = listOf(
            Action(
                name = stringResource(id = R.string.training),
                experiencePoints = Constants.TRAINING_EXPERIENCE,
                isPositive = true
            ),
            Action(
                name = stringResource(id = R.string.walking),
                experiencePoints = Constants.WALKING_EXPERIENCE,
                isPositive = true
            ),
            Action(
                name = stringResource(id = R.string.smoking_hookah),
                experiencePoints = Constants.HOOKAH_EXPERIENCE,
                isPositive = false
            )
        )
        SuccessScreen(
            actions = sampleActions,
            experience = 250,
            showMilestoneNotification = false,
            onActionClick = {},
            onAddAction = { _, _, _ -> },
            onUpdateAction = {},
            onDeleteAction = {},
            onDismissMilestoneNotification = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun MainScreenPhonePreview() {
    LifeXPTheme {
        val sampleActions = listOf(
            Action(
                name = stringResource(id = R.string.training),
                experiencePoints = Constants.TRAINING_EXPERIENCE,
                isPositive = true
            ),
            Action(
                name = stringResource(id = R.string.walking),
                experiencePoints = Constants.WALKING_EXPERIENCE,
                isPositive = true
            ),
            Action(
                name = stringResource(id = R.string.smoking_hookah),
                experiencePoints = Constants.HOOKAH_EXPERIENCE,
                isPositive = false
            )
        )
        SuccessScreen(
            actions = sampleActions,
            experience = 250,
            showMilestoneNotification = false,
            onActionClick = {},
            onAddAction = { _, _, _ -> },
            onUpdateAction = {},
            onDeleteAction = {},
            onDismissMilestoneNotification = {}
        )
    }
}