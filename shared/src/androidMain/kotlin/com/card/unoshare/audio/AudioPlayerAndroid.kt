package com.card.unoshare.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import com.card.unoshare.R

/**
 * @author xinggen.guo
 * @date 2025/8/13 16:21
 * @description
 */
class AudioPlayerAndroid(private val context: Context) : AudioPlayer {
    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(5)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        ).build()

    private var cardSoundId = 0
    private var winSoundId = 0
    private var loseSoundId = 0
    private var drawSoundId = 0
    private var unoSoundId = 0

    private var bgmPlayer: MediaPlayer? = null

    init {
        cardSoundId = soundPool.load(context, R.raw.snd_play, 1)
        winSoundId = soundPool.load(context, R.raw.snd_win, 1)
        loseSoundId = soundPool.load(context, R.raw.snd_lose, 1)
        drawSoundId = soundPool.load(context, R.raw.snd_draw, 1)
        unoSoundId = soundPool.load(context, R.raw.snd_uno, 1)

        bgmPlayer = MediaPlayer.create(context, R.raw.bgm)
        bgmPlayer?.isLooping = true
    }

    override fun playCard() {
        soundPool.play(cardSoundId, 1f, 1f, 1, 0, 1f)
    }

    override fun playWin() {
        soundPool.play(winSoundId, 1f, 1f, 1, 0, 1f)
    }

    override fun playLose() {
        soundPool.play(loseSoundId, 1f, 1f, 1, 0, 1f)
    }

    override fun playDraw() {
        soundPool.play(drawSoundId, 1f, 1f, 1, 0, 1f)
    }

    override fun playUno() {
        soundPool.play(unoSoundId, 1f, 1f, 1, 0, 1f)
    }

    override fun playBgm(loop: Boolean) {
        bgmPlayer?.isLooping = loop
        bgmPlayer?.start()
    }

    override fun stopAll() {
        soundPool.autoPause()
    }

    override fun stopBgm() {
        bgmPlayer?.pause()
    }
}
