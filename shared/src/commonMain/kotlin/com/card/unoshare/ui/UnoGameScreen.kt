package com.card.unoshare.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.card.unoshare.engine.GameEngine
import com.card.unoshare.model.Player
import com.card.unoshare.rule.SpecialRuleSet

/**
 * @author xinggen.guo
 * @date 31/07/2025 14:52
 * @description
 */

@Composable
fun UnoGameScreen() {
    val p1 = remember { Player("1", "Alice") }
    val p2 = remember { Player("2", "Bob") }
    val game = remember { GameEngine(listOf(p1, p2), SpecialRuleSet()) }

    var currentPlayer by remember { mutableStateOf(game.getCurrentPlayer()) }
    var cards by remember { mutableStateOf(currentPlayer.hand.toList()) }

    Column(Modifier.padding(16.dp)) {
        Text("🎮 Current Player: ${currentPlayer.name}")
        Text("🎮 当前玩家：${currentPlayer.name}")

        Spacer(Modifier.height(8.dp))

        Text("🃏 Cards in hand:")
        Text("🃏 手牌：")

        LazyRow {
            items(cards.size) { index ->
                val card = cards[index]
                CardView(card.color.name, card.number.toString())
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            game.drawCard(currentPlayer)
            cards = currentPlayer.hand.toList()
        }) {
            Text("Draw Card")
            Text("抽牌")
        }
    }
}

@Composable
fun CardView(color: String, number: String) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .size(width = 60.dp, height = 90.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Color: $color")
            Text("颜色：$color")
            Text("No: $number")
            Text("数字：$number")
        }
    }
}