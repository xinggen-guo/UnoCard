package com.card.unoshare.model

/**
 * @author xinggen.guo
 * @date 31/07/2025 13:53
 * @description
 */
class GameStatus(
    val players: List<Player>,
    var currentPlayerIndex: Int = 0,
    var isClockwise: Boolean = true,
    var gameEnded: Boolean = false
) {
    fun nextPlayer() {
        currentPlayerIndex = if (isClockwise)
            (currentPlayerIndex + 1) % players.size
        else
            (currentPlayerIndex - 1 + players.size) % players.size
    }

    fun currentPlayer(): Player = players[currentPlayerIndex]
}