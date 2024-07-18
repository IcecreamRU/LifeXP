package ru.icecreamru.lifexp.domain.usecase

import ru.icecreamru.lifexp.domain.repository.ActionRepository
import javax.inject.Inject

class DeleteActionUseCase @Inject constructor(private val repository: ActionRepository) {
    suspend operator fun invoke(actionId: Int) = repository.deleteAction(actionId)
}