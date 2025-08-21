package com.card.unoshare

import com.card.unoshare.audio.AudioPlayerJvm
import com.card.unoshare.audio.GameAudio
import com.card.unoshare.language.I18nManager
import com.card.unoshare.util.AppLog
import com.card.unoshare.util.LogLevel
import kotlinx.coroutines.runBlocking

/**
 * @author xinggenguo
 * @date 2025/8/20 18:49
 * @description
 */
object DesktopInitializer {

    fun init(){
        AppLog.minLevel = LogLevel.VERBOSE  // debug
        AppLog.pretty = true
        runBlocking {
            GameAudio.init(AudioPlayerJvm())
            I18nManager.init()
        }
    }
}