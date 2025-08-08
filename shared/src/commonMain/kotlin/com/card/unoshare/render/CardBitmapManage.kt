package com.card.unoshare.render

/**
 * @author xinggen.guo
 * @date 08/08/2025 13:30
 * @description
 */
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.unit.Density
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.LayoutDirection
import com.card.unoshare.model.Card
import com.card.unoshare.model.CardColor
import com.card.unoshare.model.CardType
import com.card.unoshare.render.data.CardBackStyle
import com.card.unoshare.render.data.CardFace
import com.card.unoshare.render.data.CardStyle
import com.card.unoshare.render.data.CardThemes

object CardBitmapManager {

    private val cache = mutableMapOf<String, ImageBitmap>()

    @OptIn(ExperimentalTextApi::class)
    @Composable
    fun bitmap(
        face: CardFace,
        style: CardStyle = CardStyle()
    ): ImageBitmap {
        val key = cacheKey(face, style)
        cache[key]?.let { return it }

        val density = LocalDensity.current
        val measurer = rememberTextMeasurer()
        val img = renderCardBitmap(face, style, density, measurer)
        cache[key] = img
        return img
    }

    @OptIn(ExperimentalTextApi::class)
    fun bitmapWith(
        face: CardFace,
        style: CardStyle,
        density: Density,
        measurer: TextMeasurer
    ): ImageBitmap {
        val key = cacheKey(face, style)
        return cache.getOrPut(key) { renderCardBitmap(face, style, density, measurer) }
    }

    @Composable
    fun bitmapByCard(
        card: Card, isHand: Boolean = false
    ): ImageBitmap {
        return  bitmap(covertCardFace(card), covertCardStyle(card, isHand))
    }

    fun clearCache() = cache.clear()

    private fun cacheKey(face: CardFace, s: CardStyle): String =
        when (face) {
            is CardFace.Number -> "N${face.value}"
            CardFace.Plus2     -> "P2"
            CardFace.Reverse   -> "REV"
            CardFace.Skip      -> "SKIP"
            CardFace.Wild      -> "WILD"
            CardFace.WildDraw4 -> "WILD4"
        } + "_$s"


    fun covertCardFace(card: Card): CardFace {
        return when (card.type) {
            CardType.WILD_DRAW_FOUR  -> {
                CardFace.WildDraw4
            }
            CardType.WILD -> {
                CardFace.Wild
            }
            CardType.DRAW_TWO -> {
                CardFace.Plus2
            }
            CardType.REVERSE -> {
                CardFace.Reverse
            }
            CardType.SKIP -> {
                CardFace.Skip
            }
            CardType.NUMBER-> {
                CardFace.Number(card.number?:0)
            }
        }
    }

    fun covertCardStyle(card: Card, isHand: Boolean = false): CardStyle {

        if(isHand && card.isWild()){
            return CardThemes.BLACK
        }

        return when (card.getColor()) {
            CardColor.BLUE -> {
                CardThemes.BLUE_DARK
            }

            CardColor.RED -> {
                CardThemes.RED_DARK
            }

            CardColor.GREEN -> {
                CardThemes.GREEN_DARK
            }

            CardColor.YELLOW -> {
                CardThemes.YELLOW_DARK
            }
        }
    }

    fun renderCardBackBitmap(
        style: CardBackStyle,
        density: Density
    ): ImageBitmap {
        val w = style.size.width.toInt()
        val h = style.size.height.toInt()
        val image = ImageBitmap(w, h, ImageBitmapConfig.Argb8888)
        val canvas = androidx.compose.ui.graphics.Canvas(image)
        val scope = CanvasDrawScope()
        scope.draw(
            density = density,
            layoutDirection = LayoutDirection.Ltr,
            canvas = canvas,
            size = Size(w.toFloat(), h.toFloat())
        ) {
            drawCardBack(style)
        }
        return image
    }

    private fun DrawScope.drawCardBack(s: CardBackStyle) = with(s) {
        val w = size.width
        val h = size.height

        // 1) 外部白色圆角边框（描边）
        drawRoundRect(
            color = outerBorderColor,
            size = size,
            cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx),
            style = Stroke(width = outerBorderWidth)
        )

        // 2) 内部主色填充（留出边框厚度）
        val inset = outerBorderWidth
        drawRoundRect(
            color = innerColor,
            topLeft = Offset(inset, inset),
            size = Size(w - inset * 2, h - inset * 2),
            cornerRadius = CornerRadius(cornerRadiusPx * 0.9f, cornerRadiusPx * 0.9f)
        )

        // 3) 斜向白色椭圆带（无任何文字/商标）
        val bw = w * bandWidthFraction
        val bh = h * bandHeightFraction
        val cx = w / 2f
        val cy = h / 2f

        rotate(degrees = bandTiltDeg, pivot = Offset(cx, cy)) {
            drawOval(
                color = bandColor,
                topLeft = Offset(cx - bw / 2f, cy - bh / 2f),
                size = Size(bw, bh)
            )

            // 两侧轻微切口，避免带子太满（可删）
            val cut = bh * 0.18f
            drawRect(
                color = innerColor,
                topLeft = Offset(cx - bw / 2f - 2f, cy - bh / 2f - 2f),
                size = Size(bw * 0.18f, bh + 4f)
            )
            drawRect(
                color = innerColor,
                topLeft = Offset(cx + bw / 2f - bw * 0.18f + 2f, cy - bh / 2f - 2f),
                size = Size(bw * 0.18f, bh + 4f)
            )
        }
    }
}