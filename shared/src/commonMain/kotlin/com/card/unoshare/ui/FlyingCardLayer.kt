package com.card.unoshare.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.card.unoshare.model.Card
import com.card.unoshare.model.MovingCardState
import kotlinx.coroutines.launch

/**
 * @author xinggen.guo
 * @date 05/08/2025 13:03
 * @description
 */

@Composable
fun FlyingCardLayer(
    movingCardState: State<MovingCardState?> = CardFlyManager.movingCardState,
    onAnimationEnd: (List<Card>) -> Unit = {}
) {
    val state = movingCardState.value ?: return
    val cards = state.cards
    val scope = rememberCoroutineScope()

    // 当前动画中的卡牌 index
    var currentIndex by remember(state) { mutableStateOf(0) }

    val animX = remember { Animatable(state.from.x) }
    val animY = remember { Animatable(state.from.y) }

    // 当前卡牌动画
    LaunchedEffect(currentIndex, state) {
        if (currentIndex >= cards.size) return@LaunchedEffect

        val card = cards[currentIndex]

        animX.snapTo(state.from.x)
        animY.snapTo(state.from.y)

        val duration = 400
        val animSpec = tween<Float>(durationMillis = duration)

        launch { animX.animateTo(state.to.x, animSpec) }
        launch {
            animY.animateTo(state.to.y, animSpec)
        }.invokeOnCompletion {
            if (currentIndex == cards.lastIndex) {
                onAnimationEnd(cards)
                CardFlyManager.clear()
                state.onArrive()
            } else {
                currentIndex++
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (currentIndex < cards.size) {
            val card = cards[currentIndex]
            val imageBitmap by produceState<ImageBitmap?>(initialValue = null, card.cardHandBitmapName) {
                value = card.getCardImg(true)
            }
            imageBitmap?.let {
                Image(
                    bitmap = it,
                    contentDescription = null,
                    contentScale = ContentScale.None,
                    modifier = Modifier
                        .absoluteOffset {
                            IntOffset(
                                animX.value.toInt(),
                                animY.value.toInt()
                            )
                        }
                        .size(60.dp, 90.dp)
                )
            }
        }
    }
}
