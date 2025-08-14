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
    return NSLocale.currentLocale.languageCode
}