package com.card.unoshare.engine


import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import com.card.unoshare.render.data.CardFace
import com.card.unoshare.render.data.CardStyle
import kotlin.math.min
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI

// ---------------- 纯通用绘制工具 ----------------

@OptIn(ExperimentalTextApi::class)
private fun drawCenteredText(
    text: String,
    style: CardStyle,
    measurer: androidx.compose.ui.text.TextMeasurer,
    density: Density,
    scope: DrawScope
) = with(scope) {
    val w = size.width
    val h = size.height
    val fontPx = min(w, h) * 0.55f
    val fontSp = with(density) { fontPx.toSp() }

    val layout = measurer.measure(
        text = text,
        style = TextStyle(
            color = style.textColor,
            fontSize = fontSp,
            fontWeight = FontWeight.Bold
        )
    )
    val x = (w - layout.size.width) / 2f
    val y = (h - layout.size.height) / 2f
    drawText(layout, topLeft = Offset(x, y))
}

@OptIn(ExperimentalTextApi::class)
private fun drawCornerText(
    text: String,
    style: CardStyle,
    measurer: androidx.compose.ui.text.TextMeasurer,
    density: Density,
    scope: DrawScope
) = with(scope) {
    val w = size.width
    val h = size.height
    val fontPx = h * 0.16f
    val fontSp = with(density) { fontPx.toSp() }

    val layout = measurer.measure(
        text = text,
        style = TextStyle(
            color = style.textColor,
            fontSize = fontSp,
            fontWeight = FontWeight.SemiBold
        )
    )

    // 左上
    val lt = Offset(style.borderPx * 1.2f, style.borderPx * 1.2f)
    drawText(layout, topLeft = lt)

    // 右下（旋转 180°）
    rotate(degrees = 180f, pivot = Offset(w, h)) {
        val rb = Offset(style.borderPx * 1.2f, style.borderPx * 1.2f)
        drawText(layout, topLeft = rb)
    }
}

private fun drawReverseSymbol(style: CardStyle, scope: DrawScope) = with(scope) {
    val w = size.width; val h = size.height
    val cx = w / 2f; val cy = h / 2f
    val r = min(w, h) * 0.28f
    val stroke = Stroke(width = min(w, h) * 0.05f, cap = StrokeCap.Round)

    // 上半圆弧 + 箭头
    drawArc(
        color = style.textColor,
        startAngle = 220f,
        sweepAngle = 160f,
        useCenter = false,
        topLeft = Offset(cx - r, cy - r),
        size = Size(r * 2, r * 2),
        style = stroke
    )
    drawArrowTip(
        tip = polar(cx, cy, r, 220f),
        angleDeg = 230f,
        sizePx = r * 0.22f,
        color = style.textColor
    )

    // 下半圆弧 + 箭头
    drawArc(
        color = style.textColor,
        startAngle = 40f,
        sweepAngle = 160f,
        useCenter = false,
        topLeft = Offset(cx - r, cy - r),
        size = Size(r * 2, r * 2),
        style = stroke
    )
    drawArrowTip(
        tip = polar(cx, cy, r, 40f),
        angleDeg = 50f,
        sizePx = r * 0.22f,
        color = style.textColor
    )
}

private fun drawSkipSymbol(style: CardStyle, scope: DrawScope) = with(scope) {
    val w = size.width; val h = size.height
    val cx = w / 2f; val cy = h / 2f
    val r = min(w, h) * 0.30f
    val stroke = Stroke(width = min(w, h) * 0.06f)

    drawCircle(color = style.textColor, radius = r, center = Offset(cx, cy), style = stroke)
    drawLine(
        color = style.textColor,
        start = Offset(cx - r * 0.7f, cy + r * 0.7f),
        end   = Offset(cx + r * 0.7f, cy - r * 0.7f),
        strokeWidth = stroke.width
    )
}

private fun drawWildPie(style: CardStyle, scope: DrawScope) = with(scope) {
    val w = size.width; val h = size.height
    val cx = w / 2f; val cy = h / 2f
    val r = min(w, h) * 0.28f

    val colors = listOf(
        Color(0xFFE45757), // 红
        Color(0xFF4AAE5F), // 绿
        Color(0xFF3D6ACB), // 蓝
        Color(0xFFE6A740)  // 黄
    )
    var start = -45f
    colors.forEach {
        drawArc(
            color = it,
            startAngle = start,
            sweepAngle = 90f,
            useCenter = true,
            topLeft = Offset(cx - r, cy - r),
            size = Size(r * 2, r * 2)
        )
        start += 90f
    }
}

// 小三角箭头
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