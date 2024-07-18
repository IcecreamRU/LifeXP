package ru.icecreamru.lifexp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.icecreamru.lifexp.data.local.dao.ActionDao
import ru.icecreamru.lifexp.data.local.dao.UserExperienceDao
import ru.icecreamru.lifexp.data.local.entity.ActionEntity
import ru.icecreamru.lifexp.data.local.entity.UserExperienceEntity

@Database(entities = [ActionEntity::class, UserExperienceEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun actionDao(): ActionDao
    abstract fun userExperienceDao(): UserExperienceDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "lifexp_database")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            getInstance(context).actionDao().insertActions(getInitialActions())
                            getInstance(context).userExperienceDao().insertUserExperience(
                                UserExperienceEntity(experience = 0)
                            )
                        }
                    }
                })
                .build()

        private fun getInitialActions() = listOf(
            ActionEntity(1, "Training", 50, true),
            ActionEntity(2, "Walking", 30, true),
            ActionEntity(3, "Smoking Hookah", -40, false)
        )
    }
}