package com.card.unoshare.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.card.unoshare.engine.CardGameResource
import com.card.unoshare.engine.GameEngine
import com.card.unoshare.engine.GameInitializer
import com.card.unoshare.model.Card
import com.card.unoshare.model.CardColor
import com.card.unoshare.model.CardType
import com.card.unoshare.model.Player
import com.card.unoshare.render.CardBackManager
import com.card.unoshare.render.CardBitmapManager
import com.card.unoshare.rule.RuleChecker
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.atan2


/**
 * @author xinggen.guo
 * @date 31/07/2025 14:52
 * @description
 */

const val offsetCardPadding = 15
val textShowColor = Color.Gray
val textPlayColor = Color.White

var drawCardOffset: Offset? = null
var disCardOffset: Offset? = null

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
        Box(modifier = Modifier.fillMaxSize()) {
            StartCardGameScreen()
            FlyingCardLayer()
        }
    }
}


@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {

    val startOrRefreshImg = CardBackManager.bitmap()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            bitmap = startOrRefreshImg,
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

@Composable
fun StartCardGameScreen() {

    val gameEngine = GameInitializer.gameEngine
    var currentPlayerName by remember { mutableStateOf("") }
    var topCard by remember { mutableStateOf("") }
    var allHands by remember { mutableStateOf(mapOf<Player, List<Card>>()) }
    var winner by remember { mutableStateOf<String?>(null) }
    var gameClockWise by remember { mutableStateOf(gameEngine.gameClockwise()) }
    var lastPlayerPlayCard by remember { mutableStateOf("") }
    var needCycleHandCard by remember { mutableStateOf(1)  }

    var renderLeftRefreshCards by remember { mutableStateOf(false) }
    var renderRightRefreshCards by remember { mutableStateOf(false) }

    fun checkNextPlayerDealEffect(gameEngine: GameEngine,checkPlayerDealEffect:(cards : List<Card>,player:Player) -> Unit) {
        val player = gameEngine.getCurrentPlayer()
        if(!player.dealDrawCard){
            needCycleHandCard += 1
            return
        }

        val cardNumber = gameEngine.getTopCard().getDrawNumber()
        val cards = gameEngine.drawCardFromPile(cardNumber)
        if(player.drawCardOffset != null && drawCardOffset != null){
            player.dealDrawCard = false
            checkPlayerDealEffect(cards, player)
        }
    }

    fun refreshGameState(checkPlayerDealEffect: ((cards: List<Card>, player: Player) -> Unit)?) {
        currentPlayerName = gameEngine.getCurrentPlayerName()
        topCard = gameEngine.getTopCard().displayText()
        allHands = gameEngine.getAllPlayerHands()
        winner = gameEngine.getWinnerName()
        gameClockWise = gameEngine.gameClockwise()
        lastPlayerPlayCard = gameEngine.getLastDirectionAndCardInfo()
        checkPlayerDealEffect?.let {
            checkNextPlayerDealEffect(gameEngine,it)
        }
        if(checkPlayerDealEffect == null){
            needCycleHandCard += 1
        }
    }

    fun autoCheckNextPlayer(winner: String?, gameEngine: GameEngine) {
        if (winner == null) {
            gameEngine.playRoundByAi(playCard = { card ->
                if (card.cardLocation != null && disCardOffset != null) {
                    CardFlyManager.start(card, card.cardLocation!!, disCardOffset!!) {
                        card.cardLocation = null
                        refreshGameState { cards, player ->
                            CardFlyManager.start(cards, drawCardOffset!!, player.drawCardOffset!!, player.isAI, onEachCardArrive = {

                            }, onArrive = {
                                gameEngine.drawCardComplete(cards)
                                refreshGameState(null)
                                renderLeftRefreshCards = !renderLeftRefreshCards
                                renderRightRefreshCards = !renderRightRefreshCards
                            })
                        }
                    }
                }
                return@playRoundByAi true
            }, drawCard = { player, card ->
                if (player.drawCardOffset != null && drawCardOffset != null) {
                    CardFlyManager.start(card, drawCardOffset!!, player.drawCardOffset!!) {
                        refreshGameState { cards, player ->
                            CardFlyManager.start(card, drawCardOffset!!, player.drawCardOffset!!) {
                                gameEngine.drawCardComplete(cards)
                                refreshGameState(null)
                                renderLeftRefreshCards = !renderLeftRefreshCards
                                renderRightRefreshCards = !renderRightRefreshCards
                            }

                        }
                    }
                }
                return@playRoundByAi true
            })
        }
    }


    // 🟡 Initialize the game once
    LaunchedEffect(Unit) {
        refreshGameState { _, _ ->
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        renderDiscardArea(gameEngine)
        var renderBottomRefreshCards by remember { mutableStateOf(false) }

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            renderDrawArea(gameEngine, maxWidth) { card, player ->
                CardFlyManager.start(card, drawCardOffset!!, player.drawCardOffset!!) {
                    card.cardLocation = null
                    if(!gameEngine.checkDrawCard(card,player)){
                        needCycleHandCard += 1
                    }
                    renderBottomRefreshCards = !renderBottomRefreshCards
                }
            }
        }

        Text(
            lastPlayerPlayCard,
            color = textShowColor,
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 15.dp)
        )

        winner?.let {
            Spacer(modifier = Modifier.height(16.dp))

            Column (modifier = Modifier.align(Alignment.TopCenter).padding(top = 30.dp)){
                Text(
                    text = "🎉 Winner: $it 🎉",
                    fontSize = 20.sp,
                    color = Color.Red
                )

                Text(
                    text = CardGameResource.i18n.info_gameOver(),
                    fontSize = 20.sp,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 5.dp).clickable {
                        gameEngine.startGame()
                        currentPlayerName = gameEngine.getCurrentPlayerName()
                        topCard = gameEngine.getTopCard().displayText()
                        allHands = gameEngine.getAllPlayerHands()
                        gameClockWise = gameEngine.gameClockwise()
                        winner = null
                        needCycleHandCard += 1
                    }
                )
            }
        }

        val imageClockBitmap by produceState<ImageBitmap?>(initialValue = null, gameClockWise) {
            value = if (gameClockWise) {
                CardGameResource.getClockWise()
            } else {
                CardGameResource.getCounterWise()
            }
        }

        var clockHeight = 0
        imageClockBitmap?.let {
            clockHeight = it.height
            Image(
                bitmap = it,
                contentDescription = null,
                contentScale = ContentScale.None,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        gameEngine.getAllPlayers().forEach {
            val canPlay = it == gameEngine.getCurrentPlayer()
            val textColor = if (canPlay) textPlayColor else textShowColor
            when (it.direction) {
                Alignment.BottomCenter -> {
                    val tipText = if (canPlay) gameEngine.getTipInfoYouPlayer() else gameEngine.getPlayerPositionStr(it)
                    key(currentPlayerName) {
                        Text(
                            tipText,
                            color = textColor,
                            modifier = Modifier.align(Alignment.Center)
                                .padding(top = clockHeight.dp + 10.dp)
                        )
                    }

                    key(renderBottomRefreshCards) {
                        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                            key(it.hand.size) {
                                BottomHandStack(it.hand, it, gameEngine, playCard = { card ->
                                    if (disCardOffset != null && card.cardLocation != null) {
                                        CardFlyManager.start(card, card.cardLocation!!, disCardOffset!!) {
                                            card.cardLocation = null
                                            refreshGameState { cards, player ->
                                                CardFlyManager.start(cards, drawCardOffset!!, player.drawCardOffset!!, player.isAI,
                                                    onEachCardArrive = {card: Card ->
//                                                     gameEngine.drawCard(card)
                                                }, onArrive = {
                                                    gameEngine.drawCardComplete(cards)
                                                    refreshGameState(null)
                                                })
                                            }
                                        }
                                    }
                                })
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
                                .padding(start = 10.dp)
                        )
                    }
                }

                Alignment.CenterEnd -> {
                    Row(modifier = Modifier.fillMaxHeight().align(alignment = Alignment.CenterEnd)) {
                        Text(
                            gameEngine.getPlayerPositionStr(it),
                            color = textColor,
                            modifier = Modifier.align(Alignment.CenterVertically)
                                .padding(end = 10.dp)
                        )
                        RightHandStack(it.hand, it, gameEngine)
                    }
                }
            }
        }


    }

    LaunchedEffect(needCycleHandCard){
        val player = gameEngine.getCurrentPlayer()
        if(player.isAI) {
            delay(500)
            autoCheckNextPlayer(winner, gameEngine)
        }
    }
}



@Composable
fun renderDrawArea(gameEngine: GameEngine, maxWidth: Dp, renderCard: (card: Card, player: Player) -> Unit) {
    val startOrRefreshImg = CardBackManager.bitmap()
    val area = maxWidth * 0.5f  // 只占一半屏幕宽度剧中
    val totalWidth = startOrRefreshImg.width.dp
    val offsetX = -(area - totalWidth) / 2
    Image(
        bitmap = startOrRefreshImg,
        contentDescription = null,
        contentScale = ContentScale.None,
        modifier = Modifier.offset(x = offsetX).onGloballyPositioned {
            drawCardOffset = it.localToWindow(Offset.Zero)
        }.clickable(enabled = !gameEngine.getCurrentPlayer().isAI, onClick = {
            val player = gameEngine.getCurrentPlayer()
            val card = gameEngine.drawCardFromPile()
            renderCard(card.get(0), player)
        })
    )
}

@Composable
fun renderDiscardArea(gameEngine: GameEngine) {
    gameEngine.getDiscardPile().let {
        val playCards = if (it.size > 3) {
            it.subList(it.size - 3, it.size).toList()
        } else {
            it.toList()
        }
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            playCards.forEachIndexed { index, card ->
                val imageBitmap = CardBitmapManager.bitmapByCard(card = card,isHand = false)
                val area = maxWidth * 0.5f  // 只占一半屏幕宽度剧中
                val totalWidth = (playCards.size - 1) * offsetCardPadding
                val startX = (area - totalWidth.dp) / 2  // 起始点偏移到 area 中心
                val offsetX = startX + (index * offsetCardPadding).dp
                Image(
                    bitmap = imageBitmap,
                    contentDescription = null,
                    contentScale = ContentScale.None,
                    modifier = Modifier.offset(x = offsetX).onGloballyPositioned {
                        if (index == playCards.size - 1) {
                            disCardOffset = it.localToWindow(Offset.Zero)
                        }
                    }
                )

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
            val imageBitmap = CardBitmapManager.bitmapByCard(card = card,isHand = true)
            val canPlayCard = player == gameEngine.getCurrentPlayer()
            val colorFilter = if (!canPlayCard) ColorFilter.tint(
                color = Color(0xFF555555),
                blendMode = BlendMode.Modulate
            )
            else null

            val totalHeight = (cards.size - 1) * offsetCardPadding
            val startY = -totalHeight / 2  // 第0张卡的offset起点
            val offsetY = startY + index * offsetCardPadding

            Image(
                bitmap = imageBitmap,
                colorFilter = colorFilter,
                contentDescription = null,
                contentScale = ContentScale.None,
                modifier = Modifier.offset(y = offsetY.dp).onGloballyPositioned {
                    if (index == cards.size - 1) {
                        player.drawCardOffset = it.localToWindow(Offset.Zero)
                    }
                    card.cardLocation = it.localToWindow(Offset.Zero)
                }
            )
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
            val imageBitmap = CardBitmapManager.bitmapByCard(card = card,isHand = true)
            val canPlayCard = player == gameEngine.getCurrentPlayer()
            val colorFilter = if (!canPlayCard) ColorFilter.tint(
                color = Color(0xFF555555),
                blendMode = BlendMode.Modulate
            )
            else null

            val totalHeight = (cards.size - 1) * offsetCardPadding
            val startY = -totalHeight / 2  // 第0张卡的offset起点
            val offsetY = startY + index * offsetCardPadding

            Image(
                bitmap = imageBitmap,
                colorFilter = colorFilter,
                contentDescription = null,
                contentScale = ContentScale.None,
                modifier = Modifier.offset(y = offsetY.dp).onGloballyPositioned {
                    if (index == cards.size - 1) {
                        player.drawCardOffset = it.localToWindow(Offset.Zero)
                    }
                    card.cardLocation = it.localToWindow(Offset.Zero)
                }
            )
        }
    }
}

@Composable
fun BottomHandStack(
    cards: MutableList<Card>,
    player: Player,
    gameEngine: GameEngine,
    playCard: (card: Card) -> Unit
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

            val imageBitmap = CardBitmapManager.bitmapByCard(card = card,isHand = true)
            imageBitmap.let {
                val totalWidth = (cards.size - 1) * offsetCardPadding
                val startX = -totalWidth / 2  // 第0张卡的offset起点
                val offsetX = startX + index * offsetCardPadding
                val colorFilter = if (!canPlayCard) ColorFilter.tint(
                    color = Color(0xFF555555),
                    blendMode = BlendMode.Modulate
                )
                else null
                var alphaValue by remember { mutableStateOf(1f) }
                Image(
                    bitmap = it,
                    colorFilter = colorFilter,
                    contentDescription = null,
                    contentScale = ContentScale.None,
                    modifier = Modifier.align(Alignment.Center).offset(x = offsetX.dp)
                        .onGloballyPositioned {
                            if (index == cards.size - 1) {
                                player.drawCardOffset = it.localToWindow(Offset.Zero)
                            }
                            card.cardLocation = it.localToWindow(Offset.Zero)
                        }.graphicsLayer {
                            alpha = alphaValue
                        }
                        .clickable(enabled = canPlayCard, onClick = {
                            when (card.type) {
                                CardType.WILD,CardType.WILD_DRAW_FOUR -> {
                                    ColorSelectorDialogController.show {
                                        gameEngine.playSelectColor(it, card, player)
                                        playCard(card)
                                    }
                                }

                                else -> {
                                    GameInitializer.gameEngine.playTurn(card, player)
                                    alphaValue = 0f
                                    playCard(card)
                                }
                            }

                        })
                )
            }
        }
    }
}

@Composable
fun ColorSelectorDialogHost() {
    if (ColorSelectorDialogController.isVisible) {
        Dialog(onDismissRequest = {
//            ColorSelectorDialogController.dismiss()
        }) {
            Box(
                modifier = Modifier
                    .size(260.dp)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .size(220.dp)
                        .pointerInput(Unit) {
                            detectTapGestures { offset ->
                                val sizePx = this.size
                                val center = Offset(sizePx.width / 2f, sizePx.height / 2f)
                                val dx = offset.x - center.x
                                val dy = offset.y - center.y
                                val angle = atan2(-dy, dx) * 180f / PI
                                val degree = if (angle < 0) angle + 360 else angle
                                val selectedQuadrant = when (degree) {
                                    in 0.0..90.0 -> 3  // YELLOW
                                    in 90.0..180.0 -> 2 // BLUE
                                    in 180.0..270.0 -> 1 // GREEN
                                    in 270.0..360.0 -> 0  //red
                                    else -> 0 // red
                                }
                                ColorSelectorDialogController.handleSelection(CardColor.entries.getOrNull(selectedQuadrant) ?: CardColor.BLUE)
                            }
                        }
                ) {
                    val colors = CardColor.colorList
                    for (i in 0 until 4) {
                        drawArc(
                            color = colors[i],
                            startAngle = i * 90f,
                            sweepAngle = 90f,
                            useCenter = true
                        )
                    }
                }
            }
        }
    }
}

