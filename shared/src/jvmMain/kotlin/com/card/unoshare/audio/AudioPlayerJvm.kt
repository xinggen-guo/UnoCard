package com.card.unoshare.audio

import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import kotlin.concurrent.thread

/**
 * @author xinggenguo
 * @date 2025/8/20 18:51
 * @description
 */
class AudioPlayerJvm : AudioPlayer {

    private val clips: MutableMap<String, Clip> = mutableMapOf()
    private var bgmClip: Clip? = null

    private fun loadAndPlay(audioPath: String, loop: Boolean = false) {
        try {

            val audioFile = File(audioPath)
            if (!audioFile.exists()) {
                println("❌file is not exist $audioPath")
                return
            }
            val audioStream = AudioSystem.getAudioInputStream( BufferedInputStream(FileInputStream(audioPath)))
            val clip = AudioSystem.getClip()
            clip.open(audioStream)
            if (loop) clip.loop(Clip.LOOP_CONTINUOUSLY) else clip.start()
            clips[audioPath] = clip
        } catch (e: Exception) {
            println("❌ Failed to play $audioPath: ${e.message}")
        }
    }

    override fun playCard() {
        thread { loadAndPlay(GameAudio.playSoundPath) }
    }

    override fun playWin() {
        thread { loadAndPlay(GameAudio.winSoundPath) }
    }

    override fun playLose() {
        thread { loadAndPlay(GameAudio.loseSoundPath) }
    }

    override fun playDraw() {
        thread {
            loadAndPlay(GameAudio.drawSoundPath)
        }
    }

    override fun playUno() {
        thread {
            loadAndPlay(GameAudio.unoSoundPath)
        }
    }

    override fun playBgm(loop: Boolean) {
        thread {
            try {
                val audioUrl = javaClass.getResource("/audio/bgm.wav")
                if (audioUrl == null) {
                    println("❌ BGM resource not found: bgm.wav")
                    return@thread
                }
                val stream = AudioSystem.getAudioInputStream(BufferedInputStream(audioUrl.openStream()))
                val clip = AudioSystem.getClip()
                clip.open(stream)
                if (loop) clip.loop(Clip.LOOP_CONTINUOUSLY) else clip.start()
                bgmClip = clip
            } catch (e: Exception) {
                println("❌ Failed to play BGM: ${e.message}")
            }
        }
    }

    override fun stopAll() {
        clips.values.forEach {
            it.stop()
            it.close()
        }
        clips.clear()
    }

    override fun stopBgm() {
        bgmClip?.stop()
        bgmClip?.close()
        bgmClip = null
    }
}