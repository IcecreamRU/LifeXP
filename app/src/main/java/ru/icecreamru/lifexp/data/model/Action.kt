package ru.icecreamru.lifexp.data.model

/**
 * Представляет действие, которое может выполнить пользователь, влияющее на его очки опыта.
 *
 * @property id Уникальный идентификатор действия.
 * @property name Название или описание действия.
 * @property experiencePoints Количество очков опыта, связанных с этим действием.
 *                            Должно быть положительным целым числом (1 или больше).
 * @property isPositive Указывает, влияет ли действие положительно или отрицательно на очки опыта.
 *                      Если true, очки опыта добавляются; если false, они вычитаются.
 *
 * @throws IllegalArgumentException если [experiencePoints] меньше 1.
 *
 * @sample
 * val positiveAction = Action(1, "Выполнить задачу", 10, true)
 * val negativeAction = Action(2, "Пропустить дедлайн", 5, false)
 */
data class Action (
    val id: Int = 0,
    val name: String,
    val experiencePoints: Int,
    val isPositive: Boolean
) {
    init {
        require(experiencePoints >= 1) { "Очки опыта должны быть 1 или больше" }
    }
}