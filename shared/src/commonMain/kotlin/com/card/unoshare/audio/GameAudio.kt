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