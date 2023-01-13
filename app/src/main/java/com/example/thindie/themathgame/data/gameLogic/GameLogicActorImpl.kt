package com.example.thindie.themathgame.data.gameLogic

import android.util.Log
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

object GameLogicActorImpl  : GameLogicActor {

    lateinit var questionGenerator: QuestionGenerator

    override suspend fun onAnswer(task: Flow<OnUserResponceUseCase.Responce>) {
        Log.d("SERVICE_TAG", this.hashCode().toString())
        task.collect { responce ->
            when (responce) {
                is OnUserResponceUseCase.Responce.Setting -> {
                   questionGenerator = QuestionGenerator.prepare(responce)
                }
                is OnUserResponceUseCase.Responce.Right -> {}
                is OnUserResponceUseCase.Responce.Wrong -> {}
            }

        }
    }

    override fun onQuestion(): Flow<Question> {
        Log.d("SERVICE_TAG",this.hashCode().toString())
        return flow {
            emit(
                questionGenerator.generateQuestion()
            )
        }.flowOn(Dispatchers.IO)
    }
}