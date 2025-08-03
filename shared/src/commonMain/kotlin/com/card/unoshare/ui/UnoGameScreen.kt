package com.card.unoshare.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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

const val offset = 15
val textShowColor = Color.Gray
val textPlayColor = Color.White

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
    var gameClockWise by remember { mutableStateOf(gameEngine.gameClockwise()) }
    var lastPlayerPlayCard by remember { mutableStateOf("") }

    // 🟡 Initialize the game once
    LaunchedEffect(Unit) {
        gameEngine.startGame()
        currentPlayerName = gameEngine.getCurrentPlayerName()
        topCard = gameEngine.getTopCard().displayText()
        lastPlayerPlayCard = gameEngine.getLastDirectionAndCardInfo()
        allHands = gameEngine.getAllPlayerHands()
        gameClockWise = gameEngine.gameClockwise()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        renderDiscardArea(gameEngine)
        var renderRefreshCards by remember { mutableStateOf(false) }
        renderDrawArea(gameEngine) {
            renderRefreshCards = !renderRefreshCards
        }

        Text(
            lastPlayerPlayCard,
            color = textShowColor,
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 15.dp)
        )

        winner?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "🎉 Winner: $it 🎉",
                fontSize = 20.sp,
                color = Color.Red,
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 30.dp)
            )
        }

        val imageClockBitmap by produceState<ImageBitmap?>(initialValue = null, gameClockWise) {
            value = if (gameClockWise) {
                CardGameResource.getClockWise()
            } else {
                CardGameResource.getCounterWise()
            }
        }

        imageClockBitmap?.let {
            Image(
                bitmap = it,
                contentDescription = null,
                contentScale = ContentScale.None,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        gameEngine.getAllPlayers().forEach {
            val canPlay = it == gameEngine.getCurrentPlayer()
            val textColor = if(canPlay) textPlayColor else textShowColor
            when (it.direction) {
                Alignment.BottomCenter -> {
                    key(renderRefreshCards){
                        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                            val tipText =
                                if (canPlay) gameEngine.getTipInfoYouPlayer() else gameEngine.getPlayerPositionStr(it)
                            key(currentPlayerName) { Text(
                                tipText,
                                color = textColor,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                                    .padding(bottom = 3.dp))
                            }
                            BottomHandStack(it.hand, it, gameEngine) {
                                currentPlayerName = gameEngine.getCurrentPlayerName()
                                topCard = gameEngine.getTopCard().displayText()
                                lastPlayerPlayCard = gameEngine.getLastDirectionAndCardInfo()
                                allHands = gameEngine.getAllPlayerHands()
                                winner = gameEngine.getWinnerName()
                                gameClockWise = gameEngine.gameClockwise()
                            }
                        }
                    }
                }

                Alignment.CenterStart -> {
                    Row(modifier = Modifier.fillMaxHeight().align(alignment = Alignment.CenterStart)) {
                        LeftHandStack(it.hand, it, gameEngine)
                        Text(
                            gameEngine.getPlayerPositionStr(it),
                            color = textColor,
                            modifier = Modifier.align(Alignment.CenterVertically)
                                .padding(start = 10.dp))
                    }
                }

                Alignment.CenterEnd -> {
                    Row(modifier = Modifier.fillMaxHeight().align(alignment = Alignment.CenterEnd)) {
                        Text(
                            gameEngine.getPlayerPositionStr(it),
                            color = textColor,
                            modifier = Modifier.align(Alignment.CenterVertically)
                                .padding(end = 10.dp))
                            RightHandStack(it.hand, it, gameEngine)
                    }

                }
            }
        }


    }

    Column(modifier = Modifier.fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            Button(onClick = {
                gameEngine.startGame()
                currentPlayerName = gameEngine.getCurrentPlayerName()
                topCard = gameEngine.getTopCard().displayText()
                allHands = gameEngine.getAllPlayerHands()
                gameClockWise = gameEngine.gameClockwise()
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
                    gameClockWise = gameEngine.gameClockwise()
                    lastPlayerPlayCard = gameEngine.getLastDirectionAndCardInfo()
                }
            }) {
                Text("Play Turn")
            }

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
fun renderDiscardArea(gameEngine: GameEngine) {
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
                    card.cardBitmapName
                ) {
                    value = card.getCardImg()
                }
                val area = maxWidth * 0.5f  // 只占一半屏幕宽度剧中
                val totalWidth = (playCards.size - 1) * offset
                val startX = (area - totalWidth.dp) / 2  // 起始点偏移到 area 中心
                val offsetX = startX + (index * offset).dp
                imageBitmap?.let { img ->
                    Image(
                        bitmap = img,
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
fun LeftHandStack(cards: MutableList<Card>, player: Player, gameEngine: GameEngine) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(start = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        cards.forEachIndexed { index, card ->
            val imageBitmap by produceState<ImageBitmap?>(initialValue = null) {
                value = card.getCardImg()
            }

            val canPlayCard = player == gameEngine.getCurrentPlayer()
            val colorFilter = if (!canPlayCard) ColorFilter.tint(
                color = Color(0xFF555555),
                blendMode = BlendMode.Modulate
            )
            else null

            val totalHeight = (cards.size - 1) * offset
            val startY = -totalHeight / 2  // 第0张卡的offset起点
            val offsetY = startY + index * offset

            imageBitmap?.let {
                Image(
                    bitmap = it,
                    colorFilter = colorFilter,
                    contentDescription = null,
                    contentScale = ContentScale.None,
                    modifier = Modifier.offset(y = offsetY.dp)
                )
            }
        }
    }
}

@Composable
fun RightHandStack(cards: MutableList<Card>, player: Player, gameEngine: GameEngine) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(end = 8.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        cards.forEachIndexed { index, card ->
            val imageBitmap by produceState<ImageBitmap?>(initialValue = null) {
                value = card.getCardImg()
            }
            val canPlayCard = player == gameEngine.getCurrentPlayer()
            val colorFilter = if (!canPlayCard) ColorFilter.tint(
                color = Color(0xFF555555),
                blendMode = BlendMode.Modulate
            )
            else null

            val totalHeight = (cards.size - 1) * offset
            val startY = -totalHeight / 2  // 第0张卡的offset起点
            val offsetY = startY + index * offset

            imageBitmap?.let {
                Image(
                    bitmap = it,
                    colorFilter = colorFilter,
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
            val canPlayCard = player == gameEngine.getCurrentPlayer() && RuleChecker.isPlayable(
                card,
                GameInitializer.gameEngine.getTopCard()
            )
            val imageBitmap by produceState<ImageBitmap?>(initialValue = null, canPlayCard) {
                value =  card.getCardImg()
            }

            imageBitmap?.let {
                val totalWidth = (cards.size - 1) * offset
                val startX = -totalWidth / 2  // 第0张卡的offset起点
                val offsetX = startX + index * offset
                val colorFilter = if (!canPlayCard) ColorFilter.tint(
                    color = Color(0xFF555555),
                    blendMode = BlendMode.Modulate
                )
                else null
                Image(
                    bitmap = it,
                    colorFilter = colorFilter,
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

