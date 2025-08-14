package com.card.unoshare.model

import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import com.card.unoshare.engine.CardGameResource
import com.card.unoshare.language.I18nManager
import i18n.I18nKeys

/**
 * @author xinggen.guo
 * @date 31/07/2025 13:53
 * @description
 */
data class Player(
    val id: String,
    val name: String,
    val hand: MutableList<Card> = mutableListOf(),
    var isAI: Boolean = false,
    // Alignment.CenterStart,Alignment.BottomCenter,Alignment.CenterEnd
    val direction: Alignment,
    var drawCardOffset: Offset? = null,
    var dealDrawCard :Boolean = false
) {
    fun drawCards(cards: List<Card>) {
        hand.addAll(cards)
    }

    fun drawCard(card: Card) {
        hand.add(card)
    }

    fun getDirectionPosition():String{
        val position = when (direction) {

            Alignment.BottomCenter -> {
                I18nManager.get(I18nKeys.you)
            }

            Alignment.CenterStart -> {
                I18nManager.get(I18nKeys.west)
            }

            Alignment.CenterEnd -> {
                I18nManager.get(I18nKeys.east)
            }

            else ->
                ""
        }
        return position
    }

}
