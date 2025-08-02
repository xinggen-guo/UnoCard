package com.card.unoshare.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.card.unoshare.ui.CardRenderer
import com.card.unoshare.ui.FullScreenBackgroundWithImage
import com.card.unoshare.ui.UnoGameScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullScreenBackgroundWithImage()
            UnoGameScreen()
        }
    }
}