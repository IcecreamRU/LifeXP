package ru.icecreamru.lifexp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.icecreamru.lifexp.data.local.entity.ActionEntity

@Dao
interface ActionDao {
    @Query("SELECT * FROM actions")
    fun getAllActions(): Flow<List<ActionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAction(action: ActionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActions(actions: List<ActionEntity>)

    @Query("DELETE FROM actions WHERE id = :actionId")
    suspend fun deleteAction(actionId: Int)

    @Update
    suspend fun updateAction(action: ActionEntity)
}