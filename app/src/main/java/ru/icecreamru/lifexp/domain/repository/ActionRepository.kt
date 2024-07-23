package ru.icecreamru.lifexp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.icecreamru.lifexp.data.model.Action

interface ActionRepository {
    /**
     * Получает список всех доступных действий.
     *
     * @return Список объектов Action.
     */
    suspend fun getAllActions(): Flow<List<Action>>

    /**
     * Обновляет опыт пользователя.
     *
     * @param experiencePoints Количество очков опыта для добавления (или вычитания, если отрицательное).
     */
    suspend fun updateExperience(experiencePoints: Int)

    /**
     * Получает текущий опыт пользователя.
     *
     * @return Текущее количество очков опыта пользователя.
     */
    suspend fun getCurrentExperience(): Int

    /**
     * Добавляет новое действие в систему.
     *
     * @param action Новое действие для добавления.
     */
    suspend fun addAction(action: Action)

    /**
     * Удаляет действие из системы.
     *
     * @param actionId Идентификатор действия для удаления.
     */
    suspend fun deleteAction(actionId: Int)

    /**
     * Обновляет существующее действие.
     *
     * @param action Обновленное действие.
     */
    suspend fun updateAction(action: Action)
}