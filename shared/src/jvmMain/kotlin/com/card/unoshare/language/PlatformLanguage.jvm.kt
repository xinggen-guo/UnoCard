package com.card.unoshare.language

import java.util.Locale

actual fun getSystemLanguageCode(): String {
    return Locale.getDefault().language
}