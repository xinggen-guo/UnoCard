package com.card.unoshare.util

import kotlinx.cinterop.BetaInteropApi
import platform.Foundation.NSLog
import platform.Foundation.NSString
import platform.Foundation.create


@OptIn(BetaInteropApi::class)
actual fun platformLogWrite(level: LogLevel, tag: String, message: String) {
    val logString = "${level.name}/$tag: $message"
    val nsLogString = NSString.create(string = logString)
    NSLog("%@", nsLogString)
}
