package ru.icecreamru.lifexp.data.model

data class Action(
    val id: Int,
    val name: String,
    val experiencePoints: Int,
    val isPositive: Boolean
)