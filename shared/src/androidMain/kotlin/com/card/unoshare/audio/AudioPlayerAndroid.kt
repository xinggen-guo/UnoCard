package com.card.unoshare.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

/**
 * @author xinggen.guo
 * @date 2025/8/13 16:21
 * @description
 */
class AudioPlayerAndroid(private val context: Context) : AudioPlayer {

    val base = "composeResources/com.card.unoshare.generated.resources"

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

    suspend fun init() {

        cardSoundId = loadSound(GameAudio.playSoundPath)
        winSoundId = loadSound(GameAudio.winSoundPath)
        loseSoundId = loadSound(GameAudio.loseSoundPath)
        drawSoundId = loadSound(GameAudio.drawSoundPath)
        unoSoundId = loadSound(GameAudio.unoSoundPath)

        bgmPlayer = prepareBGM(GameAudio.bgmSoundPath)
        bgmPlayer?.isLooping = true
    }

    private suspend fun loadSound(filePath: String): Int = withContext(Dispatchers.IO) {
        val allPath = base + File.separator + filePath
        val assetFileDescriptor = context.assets.openFd(allPath)
        return@withContext soundPool.load(assetFileDescriptor, 1)
    }

    private suspend fun prepareBGM(filePath: String): MediaPlayer =
        withContext(Dispatchers.IO) {
            MediaPlayer().apply {
                val allPath = base + File.separator + filePath
                val afd = context.assets.openFd(allPath)
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                prepare()
                isLooping = true
            }
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
