package com.card.unoshare.util

import com.card.unoshare.config.IS_RELEASE_BUILD
import kotlinx.datetime.Clock
import kotlin.concurrent.Volatile

/**
 * @author xinggenguo
 * @date 2025/8/21 18:52
 * @description
 */
/** Log level in ascending order of importance. */
enum class LogLevel(val priority: Int) {
    VERBOSE(0), DEBUG(1), INFO(2), WARN(3), ERROR(4), ASSERT(5)
}

/** Single entry logger shared by all modules. */
object AppLog {
    /** Minimum level to actually print. Change at runtime if needed. */
    @Volatile
    var minLevel: LogLevel = LogLevel.DEBUG

    /** Whether to include timestamp and level/tag decoration. */
    @Volatile
    var pretty: Boolean = true

    val tagName = "cardGame"

    /** Public API */
    inline fun v(tag: String = tagName, t: Throwable? = null, crossinline msg: () -> String) =
        log(LogLevel.VERBOSE, tag, t, msg)
    inline fun d(tag: String = tagName, t: Throwable? = null, crossinline msg: () -> String) =
        log(LogLevel.DEBUG, tag, t, msg)
    inline fun i(tag: String = tagName, t: Throwable? = null, crossinline msg: () -> String) =
        log(LogLevel.INFO, tag, t, msg)
    inline fun w(tag: String = tagName, t: Throwable? = null, crossinline msg: () -> String) =
        log(LogLevel.WARN, tag, t, msg)
    inline fun e(tag: String = tagName, t: Throwable? = null, crossinline msg: () -> String) =
        log(LogLevel.ERROR, tag, t, msg)
    inline fun a(tag: String = tagName, t: Throwable? = null, crossinline msg: () -> String) =
        log(LogLevel.ASSERT, tag, t, msg)

    inline fun log(
        level: LogLevel,
        tag: String,
        t: Throwable?,
        crossinline msg: () -> String
    ) {
        if (level.priority < minLevel.priority || IS_RELEASE_BUILD) return
        val m = buildMessage(level, tag, msg(), t)
        platformLogWrite(level, tag, m)
    }

    fun buildMessage(
        level: LogLevel,
        tag: String,
        message: String,
        throwable: Throwable?
    ): String {
        val base = if (pretty) {
            val ts: String = Clock.System.now().toEpochMilliseconds().toString()
            "[${ts}] ${level.name.padEnd(5)}/$tag: $message"
        } else {
            "$tag: $message"
        }
        val stack = throwable?.stackTraceToString()
        return if (!stack.isNullOrBlank()) "$base\n$stack" else base
    }
}

/** Platform writer (Logcat/NSLog/println). */
expect fun platformLogWrite(level: LogLevel, tag: String, message: String)