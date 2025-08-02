package com.card.unoshare.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.card.unoshare.engine.CardGameResource
import com.card.unoshare.engine.GameEngine
import com.card.unoshare.model.Card
import com.card.unoshare.model.Player
import com.card.unoshare.rule.SpecialRuleSet


/**
 * @author xinggen.guo
 * @date 31/07/2025 14:52
 * @description
 */

@Composable
fun rendCardInitPage(){

    val bgWelcomeImg by produceState<ImageBitmap?>(initialValue = null) {
        value = CardGameResource.getBgWelComeImage()
    }

    bgWelcomeImg?.let {
        Image(
            bitmap = it,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }

    var gameStarted by remember { mutableStateOf(false) }

    if(!gameStarted){
        WelcomeScreen(onStartClick = {
            gameStarted = true
        })
    }else{
        UnoGameScreen()
    }


}

@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {

    val startOrRefreshImg by produceState<ImageBitmap?>(initialValue = null) {
        value = CardGameResource.getStartOrRefresh()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        startOrRefreshImg?.let {
            Image(
                bitmap = it,
                contentDescription = null,
                contentScale = ContentScale.None,
                modifier = Modifier.align(Alignment.CenterHorizontally).clickable {
                    onStartClick()
                }
            )
            Text(
                CardGameResource.i18n.info_welcome(),
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }

}

@Composable
fun UnoGameScreen() {

    val p1 = remember { Player("1", "Alice", isAI = true) }
    val p2 = remember { Player("2", "Bob", isAI = true) }
    val p3 = remember { Player("3", "Bella", isAI = true) }
    val gameEngine = remember { GameEngine(listOf(p1, p2, p3), SpecialRuleSet()) }

    var currentPlayerName by remember { mutableStateOf("") }
    var topCard by remember { mutableStateOf("") }
    var allHands by remember { mutableStateOf(mapOf<String, List<Card>>()) }
    var winner by remember { mutableStateOf<String?>(null) }

    // 🟡 Initialize the game once
    LaunchedEffect(Unit) {
        gameEngine.startGame()
        currentPlayerName = gameEngine.getCurrentPlayerName()
        topCard = gameEngine.getTopCard()?.displayText() ?: "None"
        allHands = gameEngine.getAllPlayerHands()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Current Player: $currentPlayerName")
        // Current playing player

        Spacer(modifier = Modifier.height(8.dp))

        Text("Top Card: $topCard")
        // Top card of discard pile

        Spacer(modifier = Modifier.height(16.dp))

        allHands.forEach { (playerName, hand) ->
            Text("$playerName's Hand (${hand.size} cards):")
            // Show player name and card count
            Row {
                hand.forEach { card ->
                    Text(
                        text = card.displayText(),
                        modifier = Modifier
                            .padding(4.dp)
                            .border(1.dp, Color.Gray)
                            .padding(4.dp),
                        fontSize = 12.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                gameEngine.startGame()
                currentPlayerName = gameEngine.getCurrentPlayerName()
                topCard = gameEngine.getTopCard()?.displayText() ?: "None"
                allHands = gameEngine.getAllPlayerHands()
                winner = null
            }) {
                Text("Deal Cards")
                // Deal cards
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                if (winner == null) {
                    gameEngine.playRoundByAi()
                    currentPlayerName = gameEngine.getCurrentPlayerName()
                    topCard = gameEngine.getTopCard()?.displayText() ?: "None"
                    allHands = gameEngine.getAllPlayerHands()
                    winner = gameEngine.getWinnerName()
                }
            }) {
                Text("Play Turn")
                // Play card and switch
            }

        }
        winner?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text("🎉 Winner: $it 🎉", fontSize = 20.sp, color = Color.Red)
            // 胜者提示
            // Winner message
        }
    }

}