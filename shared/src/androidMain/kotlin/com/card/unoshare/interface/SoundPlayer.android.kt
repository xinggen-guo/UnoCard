package com.card.unoshare.`interface`

import android.content.Context
import android.media.MediaPlayer

/**
 * @author xinggen.guo
 * @date 31/07/2025 14:13
 * @description
 */

actual object SoundPlayer {
    private var mediaPlayer: MediaPlayer? = null

    fun init(context: Context) {
        // init context ways
    }

    actual fun play(name: String) {
        // play sounds source
    }
}