package com.card.unoshare.util

/**
 * @author xinggen.guo
 * @date 31/07/2025 14:03
 * @description
 */
fun <T> MutableList<T>.draw(count: Int): List<T> {
    return (0 until count).mapNotNull {
        if (this.isNotEmpty()) this.removeAt(0) else null
    }
}