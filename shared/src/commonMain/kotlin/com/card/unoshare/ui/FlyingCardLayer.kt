package com.card.unoshare.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.card.unoshare.config.IS_DEBUG_CARD
import com.card.unoshare.model.Card
import com.card.unoshare.model.MovingCardState
import com.card.unoshare.render.CardBackManager
import com.card.unoshare.render.CardBitmapManager
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * @author xinggen.guo
 * @date 05/08/2025 13:03
 * @description
 */

@Composable
fun FlyingCardLayer(
    movingCardState: State<MovingCardState?> = CardFlyManager.movingCardState,
    onAnimationEnd: (List<Card>) -> Unit = {},
) {
    val state = movingCardState.value ?: return
    val cards = state.cards
    val scope = rememberCoroutineScope()

    // 当前动画中的卡牌 index
    var currentIndex by remember(state) { mutableStateOf(-1) }

    val animX = remember(state.cards.size) { List(cards.size) { Animatable(state.from.x) } }
    val animY = remember(state.cards.size) { List(cards.size) { Animatable(state.from.y) } }

    val density = LocalDensity.current
    val spacingPx = with(density) { offsetCardPadding.dp.toPx() }

    LaunchedEffect(state){
        val duration = 400
        val spec = tween<Float>(durationMillis = duration)

        for (i in cards.indices) {
            currentIndex = i

            animX[i].snapTo(state.from.x)
            animY[i].snapTo(state.from.y)

            val x = if(state.isVertical !=null){
                if (!state.isVertical) {
                    state.to.x + ((currentIndex + 1 )* spacingPx)
                } else {
                    state.to.x
                }
            } else {
                state.to.x
            }

            val y = if(state.isVertical != null){
                if (state.isVertical) {
                    state.to.y + ((currentIndex + 1)* spacingPx)
                } else {
                    state.to.y
                }
            }else{
                state.to.y
            }

            coroutineScope {
                launch { animX[i].animateTo(x, spec) }
                launch { animY[i].animateTo(y, spec) }.join()
            }

            state.onEachCardArrive(cards[i])
        }

        onAnimationEnd(cards)
        CardFlyManager.clear()
        state.onComplete()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val last = currentIndex.coerceAtLeast(0)
        for (i in 0..last) {
            val card = cards[i]
            val imageBitmap = if (movingCardState.value?.cardFace == true || IS_DEBUG_CARD) {
                CardBitmapManager.bitmapByCard(card = card, isHand = true)
            } else {
                CardBackManager.bitmap()
            }
            Image(
                bitmap = imageBitmap,
                contentDescription = null,
                contentScale = ContentScale.None,
                modifier = Modifier
                    .absoluteOffset {
                        IntOffset(animX[i].value.roundToInt(),
                            animY[i].value.roundToInt())
                    }
            )
        }
    }
}
