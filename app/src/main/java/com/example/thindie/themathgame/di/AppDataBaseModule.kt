package com.example.thindie.themathgame.di

import android.app.Application
import com.example.thindie.themathgame.data.localData.dataBase.AppDataBase
import com.example.thindie.themathgame.data.localData.dataBase.GameResultDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppDataBaseModule {
    @Provides
    fun provideDataBase(application: Application): AppDataBase {
        return AppDataBase.getInstance(application)
    }

    @Provides
    fun provideDao(appDataBase: AppDataBase): GameResultDao{
        return appDataBase.getDataBase()
    }
}