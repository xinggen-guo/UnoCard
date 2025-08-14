package com.card.unoshare.util

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author xinggen.guo
 * @date 2025/8/14 18:52
 * @description
 */
lateinit var appContext: Context

 fun initAndroidContext(context: Context) {
    appContext = context
}

actual suspend fun readResourceText(path: String): String = withContext(Dispatchers.IO) {
    val inputStream = appContext.assets.open(path)
    inputStream.bufferedReader().use { it.readText() }
}