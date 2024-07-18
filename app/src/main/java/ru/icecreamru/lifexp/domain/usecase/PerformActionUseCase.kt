package ru.icecreamru.lifexp.domain.usecase

import ru.icecreamru.lifexp.data.model.Action
import ru.icecreamru.lifexp.domain.repository.ActionRepository
import javax.inject.Inject

class PerformActionUseCase @Inject constructor(private val repository: ActionRepository) {
    suspend operator fun invoke(action: Action) {
        repository.updateExperience(action.experiencePoints)
    }
}

//class PerformActionUseCase @Inject constructor(
//    private val actionRepository: ActionRepository
//) {
//    suspend operator fun invoke(action: Action) {
//        val currentExperience = actionRepository.getCurrentExperience()
//        val newExperience = currentExperience + action.experiencePoints
//        actionRepository.updateExperience(newExperience)
//    }
//}