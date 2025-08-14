package com.card.unoshare.language

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.card.unoshare.util.readResourceText
import i18n.I18nKeys
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * @author xinggen.guo
 * @date 2025/8/14 18:50
 * @description
 */
object I18nManager {
    private var strings: Map<String, String> = emptyMap()

    private var language: String = getDefaultLanguage()

     suspend fun init(lang: String? = null) {
         language = lang ?: getDefaultLanguage()
         strings = loadLanguage(language)
     }

    fun get(key: I18nKeys, vararg args: String): String {
        var value = strings[key.key] ?: key.key
        args.forEachIndexed { index, arg ->
            value = value.replaceFirst("{}", arg)
        }
        return value
    }

    private suspend fun loadLanguage(lang: String): Map<String, String> {
        return try {
            val jsonText = readResourceText("i18n/${lang}.json")
            val parsed = Json.parseToJsonElement(jsonText).jsonObject
            parsed.mapValues { it.value.jsonPrimitive.content }
        } catch (e: Exception) {
            emptyMap()
        }
    }

    private fun getDefaultLanguage(): String {
        return getSystemLanguageCode()
    }
}
