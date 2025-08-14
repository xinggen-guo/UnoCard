package com.card.unoshare.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.card.unoshare.engine.GameAudioManager
import com.card.unoshare.model.UserSettings

/**
 * @author xinggen.guo
 * @date 2025/8/14 11:20
 * @description
 */
@Composable
fun SettingsScreen(onBack: () -> Unit = {}) {
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF7B1FA2), Color(0xFF512DA8))
    )

    var musicOn by remember { mutableStateOf(UserSettings.isMusicOn) }
    var soundOn by remember { mutableStateOf(UserSettings.isSoundOn) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = backgroundGradient)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(brush = backgroundGradient),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Game Settings",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            SettingsButtonWithSwitch(
                title = "🎵 Music",
                checked = musicOn,
                onCheckedChange = {
                    musicOn = it
                    UserSettings.isMusicOn = it
                    if (it) GameAudioManager.playCardBgm() else GameAudioManager.stopBgMusic()
                }
            )

            SettingsButtonWithSwitch(
                title = "🔊 Sound",
                checked = soundOn,
                onCheckedChange = {
                    soundOn = it
                    UserSettings.isSoundOn = it
                    if (it) GameAudioManager.playDrawCardSound()
                }
            )

            SettingsButton("⬅️ Back", onClick = onBack)
        }
    }
}

@Composable
fun SettingsButton(title: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFF4A148C), shape = RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}

@Composable
fun SettingsButtonWithSwitch(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color(0xFF4A148C), shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp)
            .clickable { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF81C784),
                uncheckedThumbColor = Color(0xFFE57373)
            )
        )
    }
}