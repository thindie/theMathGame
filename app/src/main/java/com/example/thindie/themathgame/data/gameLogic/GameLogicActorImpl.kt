package com.example.thindie.themathgame.data.gameLogic

import com.example.thindie.themathgame.data.gameLogic.engine.QuestionGenerator
import com.example.thindie.themathgame.domain.entities.Question
import com.example.thindie.themathgame.domain.useCase.OnUserResponceUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

//      First <Responce> MUST be Responce.Setting from UI
//
class GameLogicActorImpl @Inject constructor() : GameLogicActor {

    lateinit var questionGenerator: QuestionGenerator

    override suspend fun onAnswer(task: Flow<OnUserResponceUseCase.Responce>) {
        task.collect { responce ->
            when (responce) {
                is OnUserResponceUseCase.Responce.Setting -> {
                    QuestionGenerator.inject(this, responce)
                }
                is OnUserResponceUseCase.Responce.Right -> {}
                is OnUserResponceUseCase.Responce.Wrong -> {}
            }
            onQuestion()
        }
    }

    override fun onQuestion(): Flow<Question> {
        return flow {
            emit(
                questionGenerator.generateQuestion()
            )
        }.flowOn(Dispatchers.IO)
    }
}