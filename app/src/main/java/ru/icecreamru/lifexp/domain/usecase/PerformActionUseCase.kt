package ru.icecreamru.lifexp.domain.usecase

import ru.icecreamru.lifexp.data.model.Action
import ru.icecreamru.lifexp.domain.repository.ActionRepository
import javax.inject.Inject

class PerformActionUseCase @Inject constructor(private val repository: ActionRepository) {
    suspend operator fun invoke(action: Action) {
        val currentExperience = repository.getCurrentExperience()
        val newExperience = calculateNewExperience(currentExperience, action)
        repository.updateExperience(newExperience)
    }

    private fun calculateNewExperience(currentExperience: Int, action: Action): Int {
        val change = if (action.isPositive) action.experiencePoints else -action.experiencePoints
        return (currentExperience + change).coerceAtLeast(0)
    }
}