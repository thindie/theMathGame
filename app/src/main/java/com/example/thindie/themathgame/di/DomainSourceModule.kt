package com.example.thindie.themathgame.di

import com.example.thindie.themathgame.data.MathGameRepositoryImpl
import com.example.thindie.themathgame.domain.useCase.MathGameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DomainSourceModule {
    @Binds
    abstract fun bindMathGameRepo(impl: MathGameRepositoryImpl): MathGameRepository
}