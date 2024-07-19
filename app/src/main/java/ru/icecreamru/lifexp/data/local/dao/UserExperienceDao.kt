package ru.icecreamru.lifexp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.icecreamru.lifexp.data.local.entity.UserExperienceEntity

@Dao
interface UserExperienceDao {
    @Query("SELECT experience FROM user_experience WHERE id = 1")
    suspend fun getUserExperience(): Int

    @Query("UPDATE user_experience SET experience = :points WHERE id = 1")
    suspend fun updateUserExperience(points: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserExperience(userExperience: UserExperienceEntity)
}