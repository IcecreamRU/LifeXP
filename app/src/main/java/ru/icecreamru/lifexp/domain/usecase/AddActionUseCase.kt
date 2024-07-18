package ru.icecreamru.lifexp.domain.usecase

import ru.icecreamru.lifexp.data.model.Action
import ru.icecreamru.lifexp.domain.repository.ActionRepository
import javax.inject.Inject

class AddActionUseCase @Inject constructor(private val repository: ActionRepository) {
    suspend operator fun invoke(action: Action) = repository.addAction(action)
}