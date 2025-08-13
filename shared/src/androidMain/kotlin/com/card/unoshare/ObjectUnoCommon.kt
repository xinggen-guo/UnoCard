package com.card.unoshare

import android.content.Context
import com.card.unoshare.audio.AudioPlayerAndroid
import com.card.unoshare.audio.GameAudio
import com.card.unoshare.config.UserConfig


/**
 * @author xinggen.guo
 * @date 01/08/2025 21:37
 * @description
 */
object ObjectUnoCommon {

    fun init(context: Context){
        val audioPlayer = AudioPlayerAndroid(context)
        GameAudio.init(audioPlayer)
        UserConfig.init(context)
    }

}