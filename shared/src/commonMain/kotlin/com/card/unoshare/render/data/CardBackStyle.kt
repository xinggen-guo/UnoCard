package com.card.unoshare.render.data

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

/**
 * @author xinggen.guo
 * @date 08/08/2025 15:26
 * @description
 */
data class CardBackStyle(
    val size: Size = Size(121f, 181f), // 默认分辨率
    val cornerRadiusPx: Float = 16f,
    val outerBorderWidth: Float = 5f,
    val outerBorderColor: Color = Color.White, // 外框
    val innerColor: Color = Color(0xFFB32024), // 主色（默认深红）
    val bandColor: Color = Color.White,        // 斜向椭圆带
    val bandTiltDeg: Float = -20f,
    val bandWidthFraction: Float = 0.88f,
    val bandHeightFraction: Float = 0.58f
)

// 主题枚举（和你前面卡牌风格保持一致：红/黑/蓝/绿/黄）
enum class CardBackTheme {
    Red, Black, Blue, Green, Yellow
}

// 主题到样式的映射（默认值全在这里）
fun CardBackTheme.toStyle(): CardBackStyle = when (this) {
    CardBackTheme.Red -> CardBackStyle(
        innerColor = Color(0xFFB32024),        // 深红
        outerBorderColor = Color.White,
        bandColor = Color.White
    )
    CardBackTheme.Black -> CardBackStyle(
        innerColor = Color(0xFF000000),        // 纯黑
        outerBorderColor = Color.White,
        bandColor = Color.White
    )
    CardBackTheme.Blue -> CardBackStyle(
        innerColor = Color(0xFF4B4BFF),
        outerBorderColor = Color.White,
        bandColor = Color.White
    )
    CardBackTheme.Green -> CardBackStyle(
        innerColor = Color(0xFF4CAF50),
        outerBorderColor = Color.White,
        bandColor = Color.White
    )
    CardBackTheme.Yellow -> CardBackStyle(
        innerColor = Color(0xFFFFC107),
        outerBorderColor = Color.White,
        bandColor = Color.White
    )
}