package com.card.unoshare.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import com.card.unoshare.language.I18nManager
import com.card.unoshare.model.UserSettings

/**
 * @author xinggen.guo
 * @date 2025/8/13 17:41
 * @description
 */

@Composable
fun AppEntry() {
    Navigator(SplashScreen)
}

@Composable
fun SplashPage() {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4E54C8), // 渐变开始：蓝紫色
                        Color(0xFF8F94FB)  // 渐变结束：浅紫色
                    )
                )
            ),contentAlignment = Alignment.Center) {
            Text(
                text = UserSettings.gameName,
                fontSize = 32.sp,
                color = Color.White
            )
        }
    }
}
