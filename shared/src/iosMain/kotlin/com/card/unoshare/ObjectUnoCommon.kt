package com.card.unoshare

import com.card.unoshare.audio.AudioPlayerIos
import com.card.unoshare.audio.GameAudio
import com.card.unoshare.language.I18nManager
import com.card.unoshare.util.applicationScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * @author xinggen.guo
 * @date 2025/8/16 18:53
 * @description
 */
object ObjectUnoCommon {

    fun initializeGame(){
        val audioPlayer = AudioPlayerIos()
        GameAudio.init(audioPlayer)
        applicationScope.launch {
            I18nManager.init()
        }
    }
}