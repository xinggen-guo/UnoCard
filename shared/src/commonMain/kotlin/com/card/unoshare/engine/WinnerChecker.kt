package com.card.unoshare.engine

/**
 * @author xinggen.guo
 * @date 31/07/2025 15:42
 * @description
 */
import com.card.unoshare.model.Player

object WinnerChecker {
    fun checkWinner(players: List<Player>): Player? {
        return players.find { it.hand.isEmpty() }
    }
}