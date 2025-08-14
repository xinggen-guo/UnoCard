package com.card.unoshare.model

import com.card.unoshare.config.ConfigKeys
import com.card.unoshare.config.UserConfig

/**
 * @author xinggen.guo
 * @date 2025/8/13 16:45
 * @description
 */
object UserSettings {

    var isSoundOn: Boolean
        get() = UserConfig.getBool(ConfigKeys.SOUND_ON, true)
        set(value) = UserConfig.setBool(ConfigKeys.SOUND_ON, value)

    var isMusicOn: Boolean
        get() = UserConfig.getBool(ConfigKeys.MUSIC_ON, true)
        set(value) = UserConfig.setBool(ConfigKeys.MUSIC_ON, value)

    var playerName: String
        get() = UserConfig.getString(ConfigKeys.PLAYER_NAME, "")
        set(value) = UserConfig.setString(ConfigKeys.PLAYER_NAME, value)

    val gameName: String
        get() = UserConfig.getGameName()
}