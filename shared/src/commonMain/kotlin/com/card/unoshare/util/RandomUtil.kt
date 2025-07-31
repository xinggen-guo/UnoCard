package com.card.unoshare.util

/**
 * @author xinggen.guo
 * @date 31/07/2025 14:03
 * @description
 */
object RandomUtil {
    fun nextInt(range: IntRange): Int {
        return range.random()
    }
}