package com.card.unoshare

import android.content.Context
import com.card.unoshare.audio.AudioPlayerAndroid
import com.card.unoshare.audio.GameAudio
import com.card.unoshare.config.UserConfig
import com.card.unoshare.language.I18nManager
import com.card.unoshare.util.AppLog
import com.card.unoshare.util.LogLevel
import kotlinx.coroutines.runBlocking


/**
 * @author xinggen.guo
 * @date 01/08/2025 21:37
 * @description
 */
object ObjectUnoCommon {

    fun init(context: Context){
        AppLog.minLevel = LogLevel.VERBOSE  // debug
        AppLog.pretty = true
        UserConfig.init(context)
        runBlocking {
            val audioPlayer = AudioPlayerAndroid(context)
            audioPlayer.init()
            GameAudio.init(audioPlayer)
            I18nManager.init()
        }
    }

}