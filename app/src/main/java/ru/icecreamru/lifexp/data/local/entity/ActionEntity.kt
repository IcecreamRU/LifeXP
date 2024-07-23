package ru.icecreamru.lifexp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actions")
data class ActionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "experience_points") val experiencePoints: Int,
    @ColumnInfo(name = "is_positive") val isPositive: Boolean
)

