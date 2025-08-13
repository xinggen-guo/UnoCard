package com.card.unoshare.config

import android.content.Context
import android.preference.PreferenceManager
import com.card.unoshare.R

/**
 * @author xinggen.guo
 * @date 2025/8/13 16:38
 * @description
 */
private lateinit var appContext: Context

actual object UserConfig {
    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private val prefs by lazy {
        PreferenceManager.getDefaultSharedPreferences(appContext)
    }

    actual fun setBool(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    actual fun getBool(key: String, defaultValue: Boolean): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    actual fun setString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    actual fun getString(key: String, defaultValue: String): String {
        return prefs.getString(key, defaultValue) ?: defaultValue
    }

    actual fun setInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    actual fun getInt(key: String, defaultValue: Int): Int {
        return prefs.getInt(key, defaultValue)
    }

    actual fun getGameName():String {
        return appContext.getString(R.string.app_name)
    }
}