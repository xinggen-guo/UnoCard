package com.card.unoshare.util

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * @author xinggen.guo
 * @date 2025/8/16 19:59
 * @description
 */
val globalExceptionHandler = CoroutineExceptionHandler { context, throwable ->
    println("🌐 Global Coroutine Exception: $throwable")
    throwable.printStackTrace()
}

val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main + globalExceptionHandler)