package com.card.unoshare.util

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile

/**
 * @author xinggen.guo
 * @date 2025/8/14 18:52
 * @description
 */
@OptIn(ExperimentalForeignApi::class)
actual suspend fun readResourceText(path: String): String = withContext(Dispatchers.Default) {
    val fileName = path.substringAfterLast("/")
    val baseName = fileName.substringBeforeLast(".")
    val extension = fileName.substringAfterLast(".")
    val bundle = NSBundle.mainBundle

    val url = bundle.pathForResource(name = baseName, ofType = extension)
    url?.let {
        val content = NSString.stringWithContentsOfFile(it, NSUTF8StringEncoding, null)
        content?.toString() ?: ""
    } ?: ""
}