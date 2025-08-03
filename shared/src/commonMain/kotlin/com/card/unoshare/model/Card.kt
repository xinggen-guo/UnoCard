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
    private var color: CardColor,
    val type: CardType,
    val number: Int? = null,
    var cardBitmapName: String? = null,
    var cardHandBitmapName:String? = null
) {
    init {
        cardBitmapName = getImageShortName()
        cardHandBitmapName = getImageShortName(true)
    }

    fun setColor(color: CardColor){
        this.color = color
        cardBitmapName = getImageShortName()
    }

    fun getColor():CardColor{
        return color
    }

    fun isWild(): Boolean = type == CardType.WILD || type == CardType.WILD_DRAW_FOUR

    fun displayText(): String {
        return if (type == CardType.NUMBER)
            "${color.name}:${number}"
        else
            "${color.name}:${type.name}"
    }

    fun randomColor() {
        setColor(CardColor.entries.random())
    }

    fun getImageShortName(hand: Boolean = false): String {
        if(!hand){
            return "${color.cardColorResourceName()}${type.getCardTypeResourceName()}${number?.toString() ?: ""}"
        }else {
            return when (type) {
                CardType.WILD -> {
                    "kw"
                }

                CardType.WILD_DRAW_FOUR -> {
                    "kw4"
                }
                else -> {
                    "${color.cardColorResourceName()}${type.getCardTypeResourceName()}${number?.toString() ?: ""}"
                }
            }
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    suspend fun getCardImg(hand: Boolean): ImageBitmap {
        if(hand){
            return Res.readBytes("files/${cardHandBitmapName}.png").toImageBitmap()
        }else {
            return Res.readBytes("files/${cardBitmapName}.png").toImageBitmap()
        }
    }
}
