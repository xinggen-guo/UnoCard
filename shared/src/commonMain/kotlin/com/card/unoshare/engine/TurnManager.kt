package com.card.unoshare.engine

/**
 * @author xinggen.guo
 * @date 31/07/2025 13:57
 * @description
 */
class TurnManager(private val playerCount: Int) {
    private var current = 0
    private var direction = 1

    fun next(): Int {
        current = (current + direction + playerCount) % playerCount
        return current
    }

    fun reverse() {
        direction *= -1
    }

    fun currentPlayer(): Int = current
}