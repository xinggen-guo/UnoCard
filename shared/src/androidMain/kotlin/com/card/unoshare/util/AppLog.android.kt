package com.card.unoshare.util

import android.util.Log

actual fun platformLogWrite(level: LogLevel, tag: String, message: String) {
    when (level) {
        LogLevel.VERBOSE -> Log.v(tag, message)
        LogLevel.DEBUG   -> Log.d(tag, message)
        LogLevel.INFO    -> Log.i(tag, message)
        LogLevel.WARN    -> Log.w(tag, message)
        LogLevel.ERROR   -> Log.e(tag, message)
        LogLevel.ASSERT  -> Log.wtf(tag, message)
    }
}