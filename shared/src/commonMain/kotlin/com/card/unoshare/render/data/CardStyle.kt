package com.card.unoshare.render.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize

/**
 * @author xinggen.guo
 * @date 08/08/2025 13:26
 * @description
 */
data class CardStyle(
    val size: IntSize = IntSize(121, 181),     // 目标分辨率
    val cornerRadiusPx: Float = 14f,
    val borderPx: Float = 10f,
    val bgPaper: Color = Color(0xFFE4C793),    // 米色纸
    val bgInner: Color = Color(0xFF2B6A66),    // 主色（深青）
    val textColor: Color = Color(0xFFEAD1A5),  // 字体米色
    val showCornerMarks: Boolean = true
) {
    override fun toString(): String {
        return "CardStyle(size=$size, cornerRadiusPx=$cornerRadiusPx, borderPx=$borderPx, bgPaper=$bgPaper, bgInner=$bgInner, textColor=$textColor, showCornerMarks=$showCornerMarks)"
    }
}

/**
 * 预设的卡片颜色主题
 */
object CardThemes {
    val BLUE = CardStyle(bgInner = Color(0xFF4B4BFF))   // 蓝色
    val GREEN = CardStyle(bgInner = Color(0xFF4CAF50))  // 绿色
    val RED = CardStyle(bgInner = Color(0xFFF44336))    // 红色
    val YELLOW = CardStyle(bgInner = Color(0xFFFFC107)) // 黄色

    // 如果需要深色衍生版本
    val BLUE_DARK = BLUE.copy(bgInner = Color(0xFF3030A0))
    val GREEN_DARK = GREEN.copy(bgInner = Color(0xFF2E7D32))
    val RED_DARK = RED.copy(bgInner = Color(0xFFC62828))
    val YELLOW_DARK = YELLOW.copy(bgInner = Color(0xFFFFA000))

    val BLACK = CardStyle(
        bgInner = Color(0xFF000000),           // 纯黑背景
        textColor = Color.White,               // 黑色背景改白字
        bgPaper = Color(0xFFFFFFFF)            // 外边纸色可调成纯白，更接近UNO万能牌
    )
}