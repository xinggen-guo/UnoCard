package com.card.unoshare

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.card.unoshare.config.UserConfig
import com.card.unoshare.ui.AppEntry
import com.card.unoshare.ui.ColorSelectorDialogHost

fun main() = application {
    DesktopInitializer.init()
    Window(onCloseRequest = ::exitApplication, title = UserConfig.getGameName()) {
        AppEntry()
        ColorSelectorDialogHost()
    }
}