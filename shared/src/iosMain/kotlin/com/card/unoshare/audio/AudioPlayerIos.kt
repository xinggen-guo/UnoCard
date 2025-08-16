package com.card.unoshare.audio

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURL
import platform.AVFAudio.AVAudioPlayer
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryAmbient
import platform.AVFAudio.setActive
import platform.Foundation.NSBundle

/**

 * @author xinggen.guo
 * @date 2025/8/16 18:22
 * @description
 * iOS implementation of the audio player.
 * Mirrors the Android-side API, so methods and signatures should be kept 1:1.
 */
class AudioPlayerIos : AudioPlayer {

    private var cardSound: AVAudioPlayer? = null
    private var winSound: AVAudioPlayer? = null
    private var loseSound: AVAudioPlayer? = null
    private var drawSound: AVAudioPlayer? = null
    private var unoSound: AVAudioPlayer? = null
    private var bgmPlayer: AVAudioPlayer? = null

    init {
        // (Optional) make sure audio plays even with silent switch / mixes with others
        tryConfigureAudioSession()

        // Effects (wav)
        cardSound = loadPlayer("snd_play", "wav")
        winSound  = loadPlayer("snd_win",  "wav")
        loseSound = loadPlayer("snd_lose", "wav")
        drawSound = loadPlayer("snd_draw", "wav")
        unoSound  = loadPlayer("snd_uno",  "wav")

        // BGM (mp3)
        bgmPlayer = loadPlayer("bgm", "mp3")?.apply {
            numberOfLoops = -1 // loop forever
        }
    }

    override fun playCard() = cardSound.playFromStart()
    override fun playWin()  = winSound.playFromStart()
    override fun playLose() = loseSound.playFromStart()
    override fun playDraw() = drawSound.playFromStart()
    override fun playUno()  = unoSound.playFromStart()

    override fun playBgm(loop: Boolean) { bgmPlayer?.play() }
    override fun stopAll() {
        bgmPlayer?.stop()
    }

    override fun stopBgm() { bgmPlayer?.stop() }

    // ---- helpers ----

    private fun AVAudioPlayer?.playFromStart() {
        this ?: return
        stop()
        currentTime = 0.0
        play()
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun loadPlayer(name: String, ext: String): AVAudioPlayer? {
        val path = NSBundle.mainBundle.pathForResource(name, ext) ?: return null
        val url  = NSURL.fileURLWithPath(path)
        return AVAudioPlayer(contentsOfURL = url, error = null)?.apply { prepareToPlay() }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun tryConfigureAudioSession() {
        runCatching {
            val session = AVAudioSession.sharedInstance()
            session.setCategory(AVAudioSessionCategoryAmbient, error = null) // or Playback if you prefer
            session.setActive(true, error = null)
        }
    }

}