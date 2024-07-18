package ru.icecreamru.lifexp.domain.usecase

import ru.icecreamru.lifexp.data.model.Action
import ru.icecreamru.lifexp.domain.repository.ActionRepository
import javax.inject.Inject

class PerformActionUseCase @Inject constructor(private val repository: ActionRepository) {
    suspend operator fun invoke(action: Action) {
        val currentExperience = repository.getCurrentExperience()
        val newExperience = (currentExperience + action.experiencePoints).coerceAtLeast(0)
        repository.updateExperience(newExperience)
    }
}