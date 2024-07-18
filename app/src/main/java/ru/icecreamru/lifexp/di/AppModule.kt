package ru.icecreamru.lifexp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.icecreamru.lifexp.data.local.AppDatabase
import ru.icecreamru.lifexp.data.local.dao.ActionDao
import ru.icecreamru.lifexp.data.local.dao.UserExperienceDao
import ru.icecreamru.lifexp.data.repository.ActionRepositoryImpl
import ru.icecreamru.lifexp.domain.repository.ActionRepository
import ru.icecreamru.lifexp.domain.usecase.PerformActionUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideActionDao(appDatabase: AppDatabase): ActionDao {
        return appDatabase.actionDao()
    }
    @Provides
    fun provideUserExperienceDao(appDatabase: AppDatabase): UserExperienceDao {
        return appDatabase.userExperienceDao()
    }

    @Provides
    @Singleton
    fun provideActionRepository(actionDao: ActionDao, userExperienceDao: UserExperienceDao): ActionRepository {
        return ActionRepositoryImpl(actionDao, userExperienceDao)
    }

    @Provides
    fun providePerformActionUseCase(actionRepository: ActionRepository): PerformActionUseCase {
        return PerformActionUseCase(actionRepository)
    }
}