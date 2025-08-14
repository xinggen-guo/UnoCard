package com.card.unoshare.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.card.unoshare.engine.CardGameResource
import com.card.unoshare.engine.GameAudioManager
import com.card.unoshare.engine.GameEngine
import com.card.unoshare.language.I18nManager
import i18n.I18nKeys
import kotlinx.coroutines.delay

/**
 * @author xinggen.guo
 * @date 2025/8/13 17:56
 * @description
 */
@Composable
fun VictoryScreen(
    gameEngine: GameEngine,
    onFinish: () -> Unit = {}
) {
    // Trigger navigation or exit after 2 seconds
    LaunchedEffect(Unit) {
        delay(2000)
        onFinish()
    }

    GameAudioManager.stopAllSound()
    GameAudioManager.playWinSound()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFEEEEEE),
        contentColor = Color.White
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFF5F6D), // 粉红红色
                        Color(0xFFFFC371)  // 淡橙红
                    )
                )
            ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                val imageVector by produceState<ImageBitmap?>(initialValue = null) {
                    value =  CardGameResource.getVictoryPic()
                }
                imageVector?.let {
                    Image(
                        bitmap = it, // Replace with your own resource
                        contentDescription = "Victory",
                        modifier = Modifier.size(200.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = I18nManager.get(I18nKeys.victoryMessage,gameEngine.getWinnerName()?:""),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
            }
        }
    }
}