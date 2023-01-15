package com.example.thindie.themathgame.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.CircularProgressIndicator
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.thindie.presentation.ui.composables.Place
import com.example.thindie.themathgame.presentation.theme.MathGameTheme
import com.example.thindie.themathgame.presentation.theme.composables.GamingPlace
import com.example.thindie.themathgame.presentation.theme.composables.Right
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
                                    Place(null)
                                    { level -> viewModel.setGame(level) } // level -> Level
                                }
                            }
                        }
                        is MainViewModel.UIResponce.Circular -> {
                            setContent {
                                MathGameTheme {
                                    CircularProgressIndicator()
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
                        else -> {}
                    }
                }
            }
        }
    }

}

