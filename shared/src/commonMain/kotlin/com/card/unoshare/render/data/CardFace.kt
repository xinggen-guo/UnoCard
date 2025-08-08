package com.card.unoshare.render.data

/**
 * @author xinggen.guo
 * @date 08/08/2025 13:27
 * @description
 */

// -------- 卡面类型 --------
sealed class CardFace {
    data class Number(val value: Int) : CardFace()
    object Plus2 : CardFace()
    object Reverse : CardFace()
    object Skip : CardFace()
    object Wild : CardFace()
    object WildDraw4 : CardFace()
}
