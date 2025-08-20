package com.card.unoshare

import com.card.unoshare.audio.AudioPlayerJvm
import com.card.unoshare.audio.GameAudio
import com.card.unoshare.language.I18nManager
import kotlinx.coroutines.runBlocking

/**
 * @author xinggenguo
 * @date 2025/8/20 18:49
 * @description
 */
object DesktopInitializer {

    fun init(){
        runBlocking {
            GameAudio.init(AudioPlayerJvm())
            I18nManager.init()
        }
    }
}