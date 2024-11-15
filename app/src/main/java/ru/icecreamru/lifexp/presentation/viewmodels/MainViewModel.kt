package ru.icecreamru.lifexp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.icecreamru.lifexp.data.model.Action
import ru.icecreamru.lifexp.domain.usecase.AddActionUseCase
import ru.icecreamru.lifexp.domain.usecase.DeleteActionUseCase
import ru.icecreamru.lifexp.domain.usecase.GetAllActionsUseCase
import ru.icecreamru.lifexp.domain.usecase.GetCurrentExperienceUseCase
import ru.icecreamru.lifexp.domain.usecase.PerformActionUseCase
import ru.icecreamru.lifexp.domain.usecase.UpdateActionUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllActionsUseCase: GetAllActionsUseCase,
    private val performActionUseCase: PerformActionUseCase,
    private val getCurrentExperienceUseCase: GetCurrentExperienceUseCase,
    private val addActionUseCase: AddActionUseCase,
    private val updateActionUseCase: UpdateActionUseCase,
    private val deleteActionUseCase: DeleteActionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    private var previousExperience = 0

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                val actions = getAllActionsUseCase()
                val experience = getCurrentExperienceUseCase()
                val showMilestoneNotification = previousExperience in 1..999 && experience >= 1000
                previousExperience = experience
                actions.collect { data ->
                    _uiState.value = UiState.Success(data, experience, showMilestoneNotification)
                }
            } catch (e: Exception) {
                Log.e(javaClass::getName.name, "loadData: ", e)
                _uiState.value = UiState.Error("Failed to load data")
            }
        }
    }

    fun performAction(action: Action) {
        viewModelScope.launch {
            try {
                performActionUseCase(action)
                loadData() // Перезагружаем данные после обновления
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to perform action")
            }
        }
    }

    fun addNewAction(name: String, experiencePoints: Int, isPositive: Boolean) {
        viewModelScope.launch {
            try {
                val newAction =
                    Action(
                        name = name,
                        experiencePoints = experiencePoints,
                        isPositive = isPositive
                    )
                addActionUseCase(newAction)
                loadData() // Перезагружаем данные после добавления
            } catch (e: Exception) {
                Log.e("MainViewModel", "addNewAction: ", e)
                _uiState.value = UiState.Error("Failed to add new action")
            }
        }
    }

    fun updateAction(action: Action) {
        viewModelScope.launch {
            try {
                updateActionUseCase(action)
                loadData() // Перезагружаем данные после добавления
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to update action")
            }
        }
    }

    fun deleteAction(actionId: Int) {
        viewModelScope.launch {
            try {
                deleteActionUseCase(actionId)
                loadData() // Перезагружаем данные после удаления
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to delete action")
            }
        }
    }

    fun dismissMilestoneNotification() {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is UiState.Success) {
                _uiState.value = currentState.copy(showMilestoneNotification = false)
            }
        }
    }
}

sealed class UiState {
    object Loading : UiState()
    data class Success(
        val actions: List<Action>,
        val experience: Int,
        val showMilestoneNotification: Boolean = false
    ) : UiState()

    data class Error(val message: String) : UiState()
}