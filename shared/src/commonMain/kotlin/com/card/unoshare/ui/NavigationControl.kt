package com.card.unoshare.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.card.unoshare.engine.GameEngine
import kotlinx.coroutines.delay

/**
 * @author xinggen.guo
 * @date 2025/8/13 19:46
 * @description
 */
// Screens
// SplashScreen.kt
object SplashScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        SplashPage()
        // Show splash and delay navigation
        LaunchedEffect(Unit) {
            delay(2000)
            navigator.push(GameScreen)
        }
    }
}

// GameScreen.kt
object GameScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        rendCardInitPage {
            navigator.push(VictoryScreen(it))
        }
    }
}

// VictoryScreen.kt
class VictoryScreen(val gameEngine: GameEngine) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        VictoryScreen(gameEngine = gameEngine, onFinish = {
            navigator.popUntilRoot()
        })
    }
}
