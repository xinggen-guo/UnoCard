package com.card.unoshare.util

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image

actual fun ByteArray.toImageBitmap(): ImageBitmap {
    // Uses Skia on iOS (Compose Multiplatform runtime)
    val skiaImage = Image.makeFromEncoded(this)
    return skiaImage.toComposeImageBitmap()
}