package com.card.unoshare.render

/**
 * @author xinggen.guo
 * @date 08/08/2025 13:28
 * @description
 */

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.card.unoshare.render.data.CardFace
import com.card.unoshare.render.data.CardStyle
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@OptIn(ExperimentalTextApi::class)
fun renderCardBitmap(
    face: CardFace,
    style: CardStyle,
    density: Density,
    measurer: TextMeasurer
): ImageBitmap {
    val w = style.size.width
    val h = style.size.height
    val image = ImageBitmap(w, h, ImageBitmapConfig.Argb8888)
    val canvas = Canvas(image)
    val scope = CanvasDrawScope()

    scope.draw(
        density = density,
        layoutDirection = LayoutDirection.Ltr,
        canvas = canvas,
        size = Size(w.toFloat(), h.toFloat())
    ) {
        // 外层米色
        drawRoundRect(
            color = style.bgPaper,
            size = size,
            cornerRadius = CornerRadius(style.cornerRadiusPx, style.cornerRadiusPx)
        )
        // 内层深青
        drawRoundRect(
            color = style.bgInner,
            topLeft = Offset(style.borderPx, style.borderPx),
            size = Size(size.width - style.borderPx * 2, size.height - style.borderPx * 2),
            cornerRadius = CornerRadius(style.cornerRadiusPx * 0.75f, style.cornerRadiusPx * 0.75f)
        )

        when (face) {
            is CardFace.Number -> {
                drawCenteredTextChange("${face.value}", style, measurer, density)
                if (style.showCornerMarks) drawCornerText("${face.value}", style, measurer, density)
            }
            CardFace.Plus2 -> {
                drawCenteredTextChange("+2", style, measurer, density)
                if (style.showCornerMarks) drawCornerText("+2", style, measurer, density)
            }
            CardFace.Reverse -> {
                drawReverseSymbol(style)
                if (style.showCornerMarks) drawCornerText("↺", style, measurer, density)
            }
            CardFace.Skip -> {
                drawSkipSymbol(style)
                if (style.showCornerMarks) drawCornerText("⛔", style, measurer, density)
            }
            CardFace.Wild -> {
                drawWildPie(style)
                if (style.showCornerMarks) drawCornerText("W", style, measurer, density)
            }
            CardFace.WildDraw4 -> {
                drawWildPie(style)
                if (style.showCornerMarks) drawCornerText("+4", style, measurer, density)
            }
        }
    }
    return image
}

// ----------- 工具绘制（纯 DrawScope）-----------

@OptIn(ExperimentalTextApi::class)
private fun DrawScope.drawCenteredText(
    text: String,
    style: CardStyle,
    measurer: TextMeasurer,
    density: Density
) {
    val fontPx = min(size.width, size.height) * 0.55f
    val layout = measurer.measure(
        text = text,
        style = TextStyle(
            color = style.textColor,
            fontSize = with(density) { fontPx.toSp() },
            fontWeight = FontWeight.Bold
        )
    )
    val x = (size.width - layout.size.width) / 2f
    val y = (size.height - layout.size.height) / 2f
    drawText(layout, topLeft = Offset(x, y))
}

private fun DrawScope.drawCenteredTextChange(
    text: String,
    style: CardStyle,
    measurer: TextMeasurer,
    density: Density
) {
    val fontPx = min(size.width, size.height) * 0.5f

    // 使用渐变色
    val gradientBrush = Brush.linearGradient(
        colors = listOf(Color.Cyan, Color.Magenta),
        start = Offset(0f, 0f),
        end = Offset(size.width, size.height)
    )

    // 添加阴影、斜体、描边等
    val layout = measurer.measure(
        text = text,
        style = TextStyle(
            brush = gradientBrush,
            fontSize = with(density) { fontPx.toSp() },
            fontWeight = FontWeight.ExtraBold,
            fontStyle = FontStyle.Italic
        )
    )

    val x = (size.width - layout.size.width) / 2f
    val y = (size.height - layout.size.height) / 2f

    // 在背景加几何图形装饰
    drawCircle(
        color = style.textColor.copy(alpha = 0.15f),
        radius = size.minDimension / 2.5f,
        center = center
    )

    // 旋转一点点，打破 UNO 统一感
    rotate(degrees = 8f, pivot = center) {
        drawText(layout, topLeft = Offset(x, y))
    }
}

@OptIn(ExperimentalTextApi::class)
private fun DrawScope.drawCornerText(
    text: String,
    style: CardStyle,
    measurer: TextMeasurer,
    density: Density
) {
    val badgeSize = size.minDimension * 0.20f
    val fontSize = size.minDimension * 0.22f
    val pad = size.minDimension * 0.06f
    val bg = style.textColor.copy(alpha = 0.16f)

    fun badgeAt(x: Float, y: Float) {
        drawRoundRect(color = bg,
            topLeft = Offset(x, y),
            size = Size(badgeSize, badgeSize),
            cornerRadius = CornerRadius(badgeSize * 0.25f))
        val layout = measurer.measure(
            text = text,
            style = TextStyle(
                brush = Brush.linearGradient(listOf(Color.Red, Color.White, Color.Blue)),
                fontSize = with(density) { (fontSize).toSp() },
                fontWeight = FontWeight.Black,
                fontStyle = FontStyle.Italic
            )
        )
        val tx = x + (badgeSize - layout.size.width) / 2f
        val ty = y + (badgeSize - layout.size.height) / 2f
        rotate(12f, Offset(x + badgeSize/2f, y + badgeSize/2f)) {
            drawText(layout, topLeft = Offset(tx, ty))
        }
    }

    badgeAt(pad, pad)
    badgeAt(size.width - pad - badgeSize, size.height - pad - badgeSize)
}

private fun DrawScope.drawReverseSymbol(style: CardStyle) {
    val r = size.minDimension * 0.22f
    val c = center
    val path = Path().apply {
        // two opposing chevrons made of diamonds
        val d = r * 0.55f
        moveTo(c.x - d, c.y - d); lineTo(c.x, c.y - r); lineTo(c.x + d, c.y - d); close()
        moveTo(c.x + d, c.y + d); lineTo(c.x, c.y + r); lineTo(c.x - d, c.y + d); close()
    }
    drawPath(path, color = style.textColor)
}

private fun DrawScope.drawSkipSymbol(style: CardStyle) {
    val s = size.minDimension * 0.28f
    val w = s * 0.22f
    drawLine(style.textColor, center - Offset(s, s), center + Offset(s, s), strokeWidth = w)
    drawLine(style.textColor, center + Offset(s, -s), center - Offset(s, -s), strokeWidth = w)
}

private fun DrawScope.drawWildPie(style: CardStyle) {
    val r = size.minDimension * 0.18f
    val colors = listOf(Color(0xFF00BFA5), Color(0xFFFF6D00), Color(0xFFAA00FF), Color(0xFF7CB342))
    val centers = listOf(
        center + Offset(-r, 0f), center + Offset(r, 0f),
        center + Offset(0f, -r), center + Offset(0f, r)
    )
    centers.zip(colors).forEach { (p, c) ->
        drawPath(Path().apply {
            moveTo(p.x, p.y - r); lineTo(p.x + r, p.y); lineTo(p.x, p.y + r); lineTo(p.x - r, p.y); close()
        }, color = c)
    }
}

private fun DrawScope.drawArrowTip(
    tip: Offset,
    angleDeg: Float,
    sizePx: Float,
    color: Color
) {
    val rad = angleDeg * PI.toFloat() / 180f
    val left = Offset(
        x = tip.x - sizePx * cos(rad - 0.8f),
        y = tip.y - sizePx * sin(rad - 0.8f)
    )
    val right = Offset(
        x = tip.x - sizePx * cos(rad + 0.8f),
        y = tip.y - sizePx * sin(rad + 0.8f)
    )
    val path = Path().apply {
        moveTo(tip.x, tip.y)
        lineTo(left.x, left.y)
        lineTo(right.x, right.y)
        close()
    }
    drawPath(path, color = color)
}

private fun polar(cx: Float, cy: Float, r: Float, angleDeg: Float): Offset {
    val rad = angleDeg * PI.toFloat() / 180f
    return Offset(cx + r * cos(rad), cy + r * sin(rad))
}