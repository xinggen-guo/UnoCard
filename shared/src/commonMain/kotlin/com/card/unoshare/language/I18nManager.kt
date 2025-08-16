package com.card.unoshare.language

import i18n.I18nKeys
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.jetbrains.compose.resources.ExperimentalResourceApi
import unocard.shared.generated.resources.Res

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

    @OptIn(ExperimentalResourceApi::class)
    private suspend fun loadLanguage(lang: String): Map<String, String> {
        return try {
            val jsonText = Res.readBytes("files/i18n/${lang}.json").decodeToString()
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
