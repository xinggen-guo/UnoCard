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
                drawCenteredText("${face.value}", style, measurer, density)
                if (style.showCornerMarks) drawCornerText("${face.value}", style, measurer, density)
            }
            CardFace.Plus2 -> {
                drawCenteredText("+2", style, measurer, density)
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

@OptIn(ExperimentalTextApi::class)
private fun DrawScope.drawCornerText(
    text: String,
    style: CardStyle,
    measurer: TextMeasurer,
    density: Density
) {
    val fontPx = size.height * 0.16f
    val layout = measurer.measure(
        text = text,
        style = TextStyle(
            color = style.textColor,
            fontSize = with(density) { fontPx.toSp() },
            fontWeight = FontWeight.SemiBold
        )
    )

    // 左上
    drawText(layout, topLeft = Offset(style.borderPx * 1.2f, style.borderPx * 1.2f))
    // 右下（旋转）
    rotate(degrees = 180f, pivot = Offset(size.width, size.height)) {
        drawText(layout, topLeft = Offset(style.borderPx * 1.2f, style.borderPx * 1.2f))
    }
}

private fun DrawScope.drawReverseSymbol(style: CardStyle) {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r  = min(size.width, size.height) * 0.28f
    val stroke = Stroke(width = min(size.width, size.height) * 0.05f, cap = StrokeCap.Round)

    drawArc(
        color = style.textColor,
        startAngle = 220f, sweepAngle = 160f, useCenter = false,
        topLeft = Offset(cx - r, cy - r), size = Size(r * 2, r * 2), style = stroke
    )
    drawArrowTip(polar(cx, cy, r, 220f), 230f, r * 0.22f, style.textColor)

    drawArc(
        color = style.textColor,
        startAngle = 40f, sweepAngle = 160f, useCenter = false,
        topLeft = Offset(cx - r, cy - r), size = Size(r * 2, r * 2), style = stroke
    )
    drawArrowTip(polar(cx, cy, r, 40f), 50f, r * 0.22f, style.textColor)
}

private fun DrawScope.drawSkipSymbol(style: CardStyle) {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r  = min(size.width, size.height) * 0.30f
    val strokeW = min(size.width, size.height) * 0.06f

    drawCircle(color = style.textColor, radius = r, center = Offset(cx, cy), style = Stroke(strokeW))
    drawLine(
        color = style.textColor,
        start = Offset(cx - r * 0.7f, cy + r * 0.7f),
        end   = Offset(cx + r * 0.7f, cy - r * 0.7f),
        strokeWidth = strokeW
    )
}

private fun DrawScope.drawWildPie(style: CardStyle) {
    val cx = size.width / 2f
    val cy = size.height / 2f
    val r  = min(size.width, size.height) * 0.28f
    val colors = listOf(
        Color(0xFFE45757), // 红
        Color(0xFF4AAE5F), // 绿
        Color(0xFF3D6ACB), // 蓝
        Color(0xFFE6A740)  // 黄
    )
    var start = -45f
    colors.forEach {
        drawArc(
            color = it, startAngle = start, sweepAngle = 90f, useCenter = true,
            topLeft = Offset(cx - r, cy - r), size = Size(r * 2, r * 2)
        )
        start += 90f
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