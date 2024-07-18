package ru.icecreamru.lifexp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_experience")
data class UserExperienceEntity(
    @PrimaryKey val id: Int = 1,
    @ColumnInfo(name = "experience") var experience: Int
)