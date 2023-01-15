package com.example.thindie.themathgame.di

import com.example.thindie.themathgame.data.localData.DataBaseActor
import com.example.thindie.themathgame.data.localData.DataBaseActorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DataBaseActorModule {
    @Binds
    abstract fun bindDataBaseActor(impl: DataBaseActorImpl): DataBaseActor
}