package com.card.unoshare.language

import java.util.Locale

/**
 * @author xinggen.guo
 * @date 2025/8/14 19:01
 * @description
 */
actual fun getSystemLanguageCode(): String {
    return Locale.getDefault().language
}