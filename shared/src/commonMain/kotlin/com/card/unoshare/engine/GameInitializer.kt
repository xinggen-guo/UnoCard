package com.card.unoshare.engine

/**
 * @author xinggen.guo
 * @date 31/07/2025 15:42
 * @description
 */
import androidx.compose.ui.Alignment
import com.card.unoshare.model.*
import com.card.unoshare.rule.SpecialRuleSet

object GameInitializer {

    val gameEngine:GameEngine

    init {
        val p1 = Player("1", "Alice", isAI = true, direction = Alignment.CenterStart)
        val p2 =Player("2", UserSettings.playerName, isAI = false, direction = Alignment.BottomCenter)
        val p3 =  Player("3", "Bella", isAI = true, direction = Alignment.CenterEnd)
        gameEngine =  GameEngine(listOf(p1, p2, p3), SpecialRuleSet())
    }
}