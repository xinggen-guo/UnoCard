package com.card.unoshare.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.card.unoshare.ObjectUnoCommon
import com.card.unoshare.ui.AppEntry
import com.card.unoshare.ui.ColorSelectorDialogHost
import com.card.unoshare.ui.rendCardInitPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppEntry()
            ColorSelectorDialogHost()
        }
    }
}