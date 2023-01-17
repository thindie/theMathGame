package com.example.thindie.themathgame.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.thindie.presentation.ui.composables.Place
import com.example.thindie.themathgame.presentation.theme.MathGameTheme
import com.example.thindie.themathgame.presentation.theme.composables.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is MainViewModel.UIResponce.Loading -> {
                            setContent {
                                MathGameTheme {
                                    Place(
                                        null,
                                        onLevelClick = { level -> viewModel.setGame(level) }) {
                                        viewModel.onShowSavedWinners()
                                    }

                                }
                            }
                        }
                        is MainViewModel.UIResponce.Circular -> {
                            setContent {
                                MathGameTheme {
                                    Circular()
                                }
                            }

                        }
                        is MainViewModel.UIResponce.AskQuestion -> {

                            val onClick: (Long?) -> Unit = { answerTime: Long? ->
                                if (answerTime != null) viewModel.onRightAnswer(answerTime)
                                else viewModel.onWrongAnswer()
                            }

                            setContent {
                                MathGameTheme {
                                    GamingPlace(
                                        it.question,
                                        viewModel,
                                    ) {
                                        onClick(it)
                                    }
                                }
                            }
                        }
                        is MainViewModel.UIResponce.Right -> {
                            setContent {
                                MathGameTheme {
                                    Right()
                                }
                            }
                        }
                        is MainViewModel.UIResponce.ShowScores -> {
                            val list = it.list

                            setContent {
                                MathGameTheme {
                                    if (!it.showAllWinners) {
                                        ShowGameResult(
                                            list,
                                            onClickAll = {
                                                viewModel.onShowSavedWinners()
                                            },
                                            onClickBack = {
                                                viewModel.loadNewGame()

                                            })
                                    }
                                    if (it.showAllWinners) {
                                        ShowWinners(list = list.asReversed()) {
                                            viewModel.loadNewGame()
                                        }
                                    }

                                }
                            }
                        }
                        is MainViewModel.UIResponce.InputWinnerName -> {
                            setContent {
                                MathGameTheme {
                                    InputWinner(onHoistClick = { gameResults ->
                                        viewModel.onAddWinner(
                                            gameResults
                                        )
                                    }, it.gameResults)
                                }
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}

