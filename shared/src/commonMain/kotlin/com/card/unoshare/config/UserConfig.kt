package com.card.unoshare.config

/**
 * @author xinggen.guo
 * @date 2025/8/13 16:38
 * @description
 */
expect object UserConfig {
    fun setBool(key: String, value: Boolean)
    fun getBool(key: String, defaultValue: Boolean = false): Boolean

    fun setString(key: String, value: String)
    fun getString(key: String, defaultValue: String = ""): String

    fun setInt(key: String, value: Int)
    fun getInt(key: String, defaultValue: Int = 0): Int
}