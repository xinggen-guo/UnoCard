package com.card.unoshare.model

import kotlin.random.Random

/**
 * @author xinggen.guo
 * @date 31/07/2025 13:53
 * @description
 */
class GameStatus(
    val players: List<Player>,
    var currentPlayerIndex: Int = 0,
    var lastPlayerIndex: Int = 0,
    var isCounterWise: Boolean = true,
    var gameEnded: Boolean = false,
    var gameStart: Boolean = false
) {
    fun nextPlayer() {
        lastPlayerIndex = currentPlayerIndex
        currentPlayerIndex = if (isCounterWise)
            (currentPlayerIndex - 1 + players.size) % players.size
        else
            (currentPlayerIndex + 1) % players.size
    }

    fun getLastPlayer(): Player = players[lastPlayerIndex]

    fun currentPlayer(): Player = players[currentPlayerIndex]

    fun reverseOrder() {
        isCounterWise = !isCounterWise
    }

    fun randomWhoFirst(){
        currentPlayerIndex = Random.nextInt(0, players.size)
        isCounterWise = Random.nextBoolean()
    }

    fun peekNextPlayer(): Player {
        val nexPlayerIndex = if (isCounterWise)
            (currentPlayerIndex - 1 + players.size) % players.size
        else
            (currentPlayerIndex + 1) % players.size
        return players[nexPlayerIndex]
    }
}