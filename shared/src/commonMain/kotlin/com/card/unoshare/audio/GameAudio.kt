package com.card.unoshare.audio

/**
 * @author xinggen.guo
 * @date 2025/8/13 16:19
 * @description
 */
interface AudioPlayer {
    fun playCard()
    fun playWin()
    fun playLose()
    fun playDraw()
    fun playUno()
    fun playBgm(loop: Boolean = true)
    fun stopAll()
    fun stopBgm()
}

object GameAudio {

    var bgmSoundPath: String = "files/audio/bgm.wav"

    var drawSoundPath: String = "files/audio/snd_draw.wav"

    var loseSoundPath: String = "files/audio/snd_lose.wav"

    var playSoundPath: String = "files/audio/snd_play.wav"

    var unoSoundPath: String = "files/audio/snd_uno.wav"

    var winSoundPath: String = "files/audio/snd_win.wav"

    lateinit var player: AudioPlayer

    fun init(playerImpl: AudioPlayer) {
        player = playerImpl
    }

    fun playCard() = player.playCard()
    fun playWin() = player.playWin()
    fun playLose() = player.playLose()
    fun playDraw() = player.playDraw()
    fun playUno() = player.playUno()
    fun playBgm(loop: Boolean = true) = player.playBgm(loop)
    fun stopAll() = player.stopAll()
    fun stopBgm() = player.stopBgm()
}