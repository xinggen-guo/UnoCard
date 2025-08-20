package com.card.unoshare

import android.content.Context
import com.card.unoshare.audio.AudioPlayerAndroid
import com.card.unoshare.audio.GameAudio
import com.card.unoshare.config.UserConfig
import com.card.unoshare.language.I18nManager
import kotlinx.coroutines.runBlocking


/**
 * @author xinggen.guo
 * @date 01/08/2025 21:37
 * @description
 */
object ObjectUnoCommon {

    fun init(context: Context){
        UserConfig.init(context)
        runBlocking {
            val audioPlayer = AudioPlayerAndroid(context)
            audioPlayer.init()
            GameAudio.init(audioPlayer)
            I18nManager.init()
        }
    }

}