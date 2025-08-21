package com.card.unoshare.util

actual fun platformLogWrite(level: LogLevel, tag: String, message: String) {
    // 统一用 stdout；必要时可以区分 ERROR -> System.err
    val line = "${level.name}/$tag: $message"
    if (level.priority >= LogLevel.ERROR.priority) {
        System.err.println(line)
    } else {
        println(line)
    }
}