package com.card.unoshare.config

import platform.Foundation.NSBundle
import platform.Foundation.NSUserDefaults

/**
 * @author xinggen.guo
 * @date 2025/8/13 16:38
 * @description
 */
actual object UserConfig {
    private val defaults: NSUserDefaults = NSUserDefaults.standardUserDefaults()
    actual fun setBool(key: String, value: Boolean) {
        defaults.setBool(value, key)
        defaults.synchronize()
    }

    actual fun getBool(key: String, defaultValue: Boolean): Boolean {
        return if (defaults.objectForKey(key) != null) defaults.boolForKey(key) else false
    }

    actual fun setString(key: String, value: String) {
        defaults.setObject(value, forKey = key)
        defaults.synchronize()
    }

    actual fun getString(key: String, defaultValue: String): String = defaults.stringForKey(key) ?: ""

    actual fun setInt(key: String, value: Int) {
        defaults.setInteger(value.toLong(), forKey = key)
        defaults.synchronize()
    }

    actual fun getInt(key: String, defaultValue: Int): Int =
        if (defaults.objectForKey(key) != null) defaults.integerForKey(key).toInt() else -1

    actual fun getGameName(): String {
        val bundle = NSBundle.mainBundle
        // Try to load from Localizable.strings in your iOS project
        return bundle.localizedStringForKey("app_name", value = "CardRush", table = null)
    }
}