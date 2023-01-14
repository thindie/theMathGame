package com.example.thindie.themathgame.di

import com.example.thindie.themathgame.data.gameLogic.GameLogicActorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataEngineSourceModule {

    @Provides
    fun provideGameLogicActor(): GameLogicActorImpl {
        return GameLogicActorImpl
    }
}