package com.card.unoshare.model

import androidx.compose.ui.graphics.ImageBitmap
import com.card.unoshare.util.toImageBitmap
import org.jetbrains.compose.resources.ExperimentalResourceApi
import unocard.shared.generated.resources.Res

/**
 * @author xinggen.guo
 * @date 31/07/2025 13:51
 * @description card base information
 */
data class Card(
    var color: CardColor,
    val type: CardType,
    val number: Int? = null,
    var cardBitmapName: String? = null,
) {

    init {
        cardBitmapName = "${getImageShortName()}"
    }

    fun isWild(): Boolean = type == CardType.WILD || type == CardType.WILD_DRAW_FOUR

    fun displayText(): String {
        return if (type == CardType.NUMBER)
            "${color.name}:${number}"
        else
            "${color.name}:${type.name}"
    }

    fun randomColor() {
        color = CardColor.entries.random()
    }

    fun getImageShortName(): String {
        return "${color.cardColorResourceName()}${type.getCardTypeResourceName()}${number?.toString() ?: ""}"
    }

    @OptIn(ExperimentalResourceApi::class)
    suspend fun getCardImg(): ImageBitmap {
        return Res.readBytes("files/${cardBitmapName}.png").toImageBitmap()
    }
}
