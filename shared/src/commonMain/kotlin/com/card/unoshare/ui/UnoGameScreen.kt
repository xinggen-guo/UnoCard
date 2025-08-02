package com.card.unoshare.ui

import androidx.compose.foundation.Image
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
import com.card.unoshare.engine.GameInitializer
import com.card.unoshare.model.Card
import com.card.unoshare.model.Player
import com.card.unoshare.rule.RuleChecker


/**
 * @author xinggen.guo
 * @date 31/07/2025 14:52
 * @description
 */

val offset = 15

@Composable
fun rendCardInitPage() {

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

    if (!gameStarted) {
        WelcomeScreen(onStartClick = {
            val gameEngine = GameInitializer.gameEngine
            gameEngine.startGame()
            gameStarted = true
        })
    } else {
        StartCardGameScreen()
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
fun StartCardGameScreen() {

    val gameEngine = GameInitializer.gameEngine
    var currentPlayerName by remember { mutableStateOf("") }
    var topCard by remember { mutableStateOf("") }
    var allHands by remember { mutableStateOf(mapOf<Player, List<Card>>()) }
    var winner by remember { mutableStateOf<String?>(null) }

    // 🟡 Initialize the game once
    LaunchedEffect(Unit) {
        gameEngine.startGame()
        currentPlayerName = gameEngine.getCurrentPlayerName()
        topCard = gameEngine.getTopCard().displayText()
        allHands = gameEngine.getAllPlayerHands()
    }
    Box(modifier = Modifier.fillMaxSize()) {

        rendDiscardArea(gameEngine)
        var renderRefreshCards by remember { mutableStateOf(false) }
        renderDrawArea(gameEngine) {
            renderRefreshCards = !renderRefreshCards
        }


        key(renderRefreshCards){

        }

        gameEngine.getAllPlayers().forEach {
            when (it.direction) {
                Alignment.BottomCenter -> {
                    var refreshHandSize  by remember { mutableStateOf(it.hand.size) }
                    key(refreshHandSize){
                        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                            BottomHandStack(it.hand, it, gameEngine) {
                                currentPlayerName = gameEngine.getCurrentPlayerName()
                                topCard = gameEngine.getTopCard().displayText()
                                allHands = gameEngine.getAllPlayerHands()
                                winner = gameEngine.getWinnerName()
                            }
                        }
                    }
                }

                Alignment.CenterStart -> {
                    Box(modifier = Modifier.align(Alignment.CenterStart)) {
                        LeftHandStack(it.hand, it, gameEngine)
                    }
                }

                Alignment.CenterEnd -> {
                    Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                        RightHandStack(it.hand, it, gameEngine)
                    }
                }
            }
        }

    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Current Player: $currentPlayerName")
        // Current playing player

        Spacer(modifier = Modifier.height(8.dp))

        Text("Top Card: $topCard")
        // Top card of discard pile
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                gameEngine.startGame()
                currentPlayerName = gameEngine.getCurrentPlayerName()
                topCard = gameEngine.getTopCard().displayText()
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
                    topCard = gameEngine.getTopCard().displayText()
                    allHands = gameEngine.getAllPlayerHands()
                    winner = gameEngine.getWinnerName()
                }
            }) {
                Text("Play Turn")
            }

        }
        winner?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text("🎉 Winner: $it 🎉", fontSize = 20.sp, color = Color.Red)
            // Winner message
        }
    }
}

@Composable
fun renderDrawArea(gameEngine: GameEngine,renderCard: () -> Unit) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val startOrRefreshImg by produceState<ImageBitmap?>(initialValue = null) {
            value = CardGameResource.getStartOrRefresh()
        }
        startOrRefreshImg?.let {
            val area = maxWidth * 0.5f  // 只占一半屏幕宽度剧中
            val totalWidth = it.width.dp
            val offsetX = - (area - totalWidth)/2
            Image(
                bitmap = it,
                contentDescription = null,
                contentScale = ContentScale.None,
                modifier = Modifier.offset(x = offsetX).clickable(enabled = !gameEngine.getCurrentPlayer().isAI, onClick = {
                    gameEngine.drawCard(gameEngine.getCurrentPlayer())
                    renderCard()
                })
            )
        }
    }
}

@Composable
fun rendDiscardArea(gameEngine: GameEngine) {
    gameEngine.getDiscardPile().let {
        val playCards = if (it.size > 3) {
            it.subList(it.size - 3, it.size)
        } else {
            it
        }
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            playCards.forEachIndexed { index, card ->
                val imageBitmap by produceState<ImageBitmap?>(
                    initialValue = null,
                    card.frontBitmapName
                ) {
                    value = card.getFrontImg()
                }
                val area = maxWidth * 0.5f  // 只占一半屏幕宽度剧中
                val totalWidth = (playCards.size - 1) * offset
                val startX = (area - totalWidth.dp) / 2  // 起始点偏移到 area 中心
                val offsetX = startX + (index * offset).dp
                imageBitmap?.let {
                    Image(
                        bitmap = it,
                        contentDescription = null,
                        contentScale = ContentScale.None,
                        modifier = Modifier.offset(x = offsetX)
                    )
                }

            }
        }
    }
}

@Composable
fun LeftHandStack(cards: MutableList<Card>, it: Player, gameEngine: GameEngine) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        cards.forEachIndexed { index, card ->
            val imageBitmap by produceState<ImageBitmap?>(initialValue = null) {
                value = card.getFrontImg()
            }

            val totalHeight = (cards.size - 1) * offset
            val startY = -totalHeight / 2  // 第0张卡的offset起点
            val offsetY = startY + index * offset

            imageBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    contentScale = ContentScale.None,
                    modifier = Modifier.offset(y = offsetY.dp)
                )
            }
        }
    }
}

@Composable
fun RightHandStack(cards: MutableList<Card>, it: Player, gameEngine: GameEngine) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(end = 8.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        cards.forEachIndexed { index, card ->
            val imageBitmap by produceState<ImageBitmap?>(initialValue = null) {
                value = card.getFrontImg()
            }

            val totalHeight = (cards.size - 1) * offset
            val startY = -totalHeight / 2  // 第0张卡的offset起点
            val offsetY = startY + index * offset

            imageBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    contentScale = ContentScale.None,
                    modifier = Modifier.offset(y = offsetY.dp)
                )
            }
        }
    }
}

@Composable
fun BottomHandStack(
    cards: MutableList<Card>,
    player: Player,
    gameEngine: GameEngine,
    playCard: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, top = 8.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        cards.forEachIndexed { index, card ->
            var canPlayCard = !player.isAI && player == gameEngine.getCurrentPlayer() && RuleChecker.isPlayable(
                card,
                GameInitializer.gameEngine.getTopCard()
            )
            val imageBitmap by produceState<ImageBitmap?>(initialValue = null, canPlayCard) {
                value = if (!player.isAI) {
                    if (canPlayCard) {
                        card.getFrontImg()
                    } else {
                        card.getDarkImg()
                    }
                } else {
                    card.getFrontImg()
                }
            }
            imageBitmap?.let {
                val totalWidth = (cards.size - 1) * offset
                val startX = -totalWidth / 2  // 第0张卡的offset起点
                val offsetX = startX + index * offset
                Image(
                    bitmap = it,
                    contentDescription = null,
                    contentScale = ContentScale.None,
                    modifier = Modifier.align(Alignment.Center).offset(x = offsetX.dp)
                        .clickable(enabled = canPlayCard, onClick = {
                            GameInitializer.gameEngine.playTurn(card, player)
                            playCard()
                        })
                )
            }
        }
    }
}

