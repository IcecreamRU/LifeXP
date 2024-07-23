package ru.icecreamru.lifexp.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.icecreamru.lifexp.data.model.Action
import ru.icecreamru.lifexp.domain.repository.ActionRepository
import javax.inject.Inject

class GetAllActionsUseCase @Inject constructor(private val repository: ActionRepository) {
    suspend operator fun invoke(): Flow<List<Action>> = repository.getAllActions()
}