package com.example.thindie.themathgame.di

import com.example.thindie.themathgame.data.gameLogic.GameLogicActor
import com.example.thindie.themathgame.data.gameLogic.GameLogicActorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
   abstract fun bindGameLogicActor(impl: GameLogicActorImpl) : GameLogicActor
}