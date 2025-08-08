package com.card.unoshare.render

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalDensity
import com.card.unoshare.render.CardBitmapManager.renderCardBackBitmap
import com.card.unoshare.render.data.CardBackStyle
import com.card.unoshare.render.data.CardBackTheme
import com.card.unoshare.render.data.toStyle

/**
 * @author xinggen.guo
 * @date 08/08/2025 15:38
 * @description
 */
object CardBackManager {
    private val cache = mutableMapOf<Pair<CardBackTheme, CardBackStyle>, ImageBitmap>()

    /** 直接用主题（默认=红色），尺寸=121×181，其他都有默认值 */
    @Composable
    fun bitmap(theme: CardBackTheme = CardBackTheme.Red): ImageBitmap {
        val style = theme.toStyle()
        return bitmap(style)
    }

    /** 自定义 style（如要改大小/角度/颜色） */
    @Composable
    fun bitmap(style: CardBackStyle): ImageBitmap {
        val key = CardBackTheme.Red to style.copy() // theme不重要，仅用于 key 唯一性
        cache[key]?.let { return it }
        val density = LocalDensity.current
        val img = renderCardBackBitmap(style, density)
        cache[key] = img
        return img
    }

    fun clear() = cache.clear()
}