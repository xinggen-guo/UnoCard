package com.card.unoshare.ui

import androidx.compose.animation.Animatable
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
    onAnimationEnd: (Card) -> Unit = {}
) {
    val state = movingCardState.value ?: return
    val scope = rememberCoroutineScope()

    val animX = remember(state) { androidx.compose.animation.core.Animatable(state.from.x) }
    val animY = remember(state) { androidx.compose.animation.core.Animatable(state.from.y) }

    LaunchedEffect(state) {
        animX.snapTo(state.from.x)
        animY.snapTo(state.from.y)

        val duration = 500
        val animSpec = tween<Float>(durationMillis = duration)

        launch { animX.animateTo(state.to.x, animSpec) }
        launch {
            animY.animateTo(state.to.y, animSpec)
        }.invokeOnCompletion {
            state.onArrive()
            onAnimationEnd(state.card)
            CardFlyManager.clear()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .absoluteOffset {
                    IntOffset(
                        animX.value.toInt(),
                        animY.value.toInt()
                    )
                }
                .size(60.dp, 90.dp)
        ) {
            CardImage(card = state.card)
        }
    }
}

@Composable
fun CardImage(card: Card) {
    val imageBitmap by produceState<ImageBitmap?>(initialValue = null) {
        value =  card.getCardImg(true)
    }
    imageBitmap?.let {
        Image(
            bitmap = it,
            colorFilter = null,
            contentDescription = null,
            contentScale = ContentScale.None)
    }
}
