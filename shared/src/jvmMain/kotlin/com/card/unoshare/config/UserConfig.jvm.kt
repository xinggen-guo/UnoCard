package com.card.unoshare.config

import java.io.File
import java.util.Properties


actual object UserConfig {
    private val file = File(System.getProperty("user.home"), ".unocard_config.properties")
    private val props = Properties().apply {
        if (file.exists()) {
            file.inputStream().use { load(it) }
        }
    }

    actual fun setBool(key: String, value: Boolean) {
        props.setProperty(key, value.toString())
        save()
    }

    actual fun getBool(key: String, defaultValue: Boolean): Boolean {
        return props.getProperty(key)?.toBooleanStrictOrNull() ?: defaultValue
    }

    actual fun setString(key: String, value: String) {
        props.setProperty(key, value)
        save()
    }

    actual fun getString(key: String, defaultValue: String): String {
        return props.getProperty(key) ?: defaultValue
    }

    actual fun setInt(key: String, value: Int) {
        props.setProperty(key, value.toString())
        save()
    }

    actual fun getInt(key: String, defaultValue: Int): Int {
        return props.getProperty(key)?.toIntOrNull() ?: defaultValue
    }

    actual fun getGameName(): String {
        return try {
            val props = Properties()
            props.load(File("config.properties").inputStream())
            props.getProperty("app_name", "CardRush")
        } catch (e: Exception) {
            "CardRush" // 失败时使用默认值
        }
    }

    private fun save() {
        file.outputStream().use { props.store(it, null) }
    }
}