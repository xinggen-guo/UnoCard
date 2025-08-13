package com.card.unoshare.engine

import com.card.unoshare.audio.GameAudio
import com.card.unoshare.model.UserSettings

/**
 * @author xinggen.guo
 * @date 2025/8/13 16:58
 * @description
 */
object GameAudioManager {

    fun playCardSound() {
        if (UserSettings.isSoundOn) {
            GameAudio.playCard()
        }
    }

    fun playCardBgm() {
        if (UserSettings.isMusicOn) {
            GameAudio.playBgm(true)
        }
    }

    fun playWinSound(){
        if (UserSettings.isSoundOn) {
            GameAudio.playWin()
        }
    }

    fun playDrawCardSound(){
        if (UserSettings.isSoundOn) {
            GameAudio.playDraw()
        }
    }

    fun playUno(){
        if (UserSettings.isSoundOn) {
            GameAudio.playUno()
        }
    }

    fun stopAllSound(){
        GameAudio.stopAll()
    }
}