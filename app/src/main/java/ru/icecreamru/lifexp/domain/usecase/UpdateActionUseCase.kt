package ru.icecreamru.lifexp.domain.usecase

import ru.icecreamru.lifexp.data.model.Action
import ru.icecreamru.lifexp.domain.repository.ActionRepository
import javax.inject.Inject

class UpdateActionUseCase @Inject constructor(private val repository: ActionRepository) {
    suspend operator fun invoke(action: Action) = repository.updateAction(action)
}