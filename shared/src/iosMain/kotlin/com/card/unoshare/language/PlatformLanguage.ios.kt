package com.card.unoshare.language

import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

/**
 * @author xinggen.guo
 * @date 2025/8/14 19:01
 * @description
 */
actual fun getSystemLanguageCode(): String {
    val raw = NSLocale.currentLocale.languageCode ?: "en"
    // 兼容 zh-Hans / en_US 等，取前段
    return raw.split('-', '_').firstOrNull().orEmpty().ifBlank { "en" }
}