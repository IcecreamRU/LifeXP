package ru.icecreamru.lifexp.domain.usecase

import ru.icecreamru.lifexp.domain.repository.ActionRepository
import javax.inject.Inject

class GetCurrentExperienceUseCase @Inject constructor(private val repository: ActionRepository) {
    suspend operator fun invoke(): Int = repository.getCurrentExperience()
}