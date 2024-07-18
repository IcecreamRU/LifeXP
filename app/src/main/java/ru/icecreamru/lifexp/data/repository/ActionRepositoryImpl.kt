package ru.icecreamru.lifexp.data.repository

import ru.icecreamru.lifexp.data.local.dao.ActionDao
import ru.icecreamru.lifexp.data.local.dao.UserExperienceDao
import ru.icecreamru.lifexp.data.local.entity.ActionEntity
import ru.icecreamru.lifexp.data.model.Action
import ru.icecreamru.lifexp.domain.repository.ActionRepository
import javax.inject.Inject

class ActionRepositoryImpl @Inject constructor(
    private val actionDao: ActionDao,
    private val userExperienceDao: UserExperienceDao
) : ActionRepository {

    override suspend fun getAllActions(): List<Action> {
        return actionDao.getAllActions().map { it.toDomainModel() }
    }

    override suspend fun updateExperience(experiencePoints: Int) {
        userExperienceDao.updateUserExperience(experiencePoints)
    }

    override suspend fun getCurrentExperience(): Int {
        return userExperienceDao.getUserExperience()
    }

    override suspend fun addAction(action: Action) {
        actionDao.insertAction(action.toEntity())
    }

    override suspend fun deleteAction(actionId: Int) {
        actionDao.deleteAction(actionId)
    }

    override suspend fun updateAction(action: Action) {
        actionDao.updateAction(action.toEntity())
    }

    private fun ActionEntity.toDomainModel() = Action(id, name, experiencePoints, isPositive)
    private fun Action.toEntity() = ActionEntity(id, name, experiencePoints, isPositive)
}