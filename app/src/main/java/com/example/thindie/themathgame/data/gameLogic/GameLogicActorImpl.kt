package com.example.thindie.themathgame.data.gameLogic

import com.example.thindie.themathgame.data.gameLogic.engine.QuestionGenerator
import com.example.thindie.themathgame.data.gameLogic.engine.ResultInGameBuilder
import com.example.thindie.themathgame.domain.entities.GameResults
import com.example.thindie.themathgame.domain.entities.Question
import com.example.thindie.themathgame.domain.useCase.OnUserResponceUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

//      First <Responce> MUST be Responce.Setting from UI
//

object GameLogicActorImpl : GameLogicActor {

    private lateinit var questionGenerator: QuestionGenerator
    private lateinit var resultInGameBuilder: ResultInGameBuilder

    override suspend fun onAnswer(task: Flow<OnUserResponceUseCase.Responce>) {

        task.collect { responce ->
            when (responce) {
                is OnUserResponceUseCase.Responce.Setting -> {
                    initQuestionGeneratorAndResultBuilder(responce)
                }
                else -> {
                    resultInGameBuilder.collectScore(responce)
                }
            }
        }
    }

    override fun onQuestion(): Flow<Question> {

        return flow {
            emit(
                questionGenerator.generateQuestion()
            )
        }.flowOn(Dispatchers.IO)
    }


    override fun onResult(gameResults: GameResults): Flow<GameResults> {
        return flow {
            emit(
                gameResults
            )
        }.flowOn(Dispatchers.IO)
    }

    override fun initQuestionGeneratorAndResultBuilder(responce: OnUserResponceUseCase.Responce.Setting) {
        questionGenerator = QuestionGenerator.build(responce)

        resultInGameBuilder = ResultInGameBuilder.build(questionGenerator.shareGamesettings(), this)
    }
}