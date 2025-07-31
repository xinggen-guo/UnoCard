package com.card.unoshare.util

/**
 * @author xinggen.guo
 * @date 31/07/2025 15:44
 * @description
 */


import com.card.unoshare.engine.*

fun runGameSimulation() {
    val (players, deck) = GameInitializer.initializeGame(listOf("Alice", "Bob", "Carol"))
    val controller = GameController(players, deck)
    controller.startGame()

    while (true) {
        val currentPlayer = controller.getCurrentPlayer()
        println("=== ${currentPlayer.name}'s Turn ===")
        controller.playTurn()

        val winner = WinnerChecker.checkWinner(players)
        if (winner != null) {
            println("🎉 ${winner.name} wins the game!")
            break
        }
    }
}