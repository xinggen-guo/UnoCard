package com.card.unoshare.util

import androidx.compose.ui.graphics.ImageBitmap
import com.card.unoshare.model.Card
import com.card.unoshare.model.CardColor
import com.card.unoshare.model.CardType

/**
 * @author xinggen.guo
 * @date 31/07/2025 14:02
 * @description
 */
object CardShuffler {
    fun createDeck(): List<Card> {
        val deck = mutableListOf<Card>()
        val colors = CardColor.entries

        //init cards
        for (color in colors) {
            // 数字卡：0 一张，1-9 各两张
            deck.add(Card(color, CardType.NUMBER, 0))
            for (i in 1..9) {
                deck.add(Card(color, CardType.NUMBER, i))
                deck.add(Card(color, CardType.NUMBER, i))
            }

            // 功能卡：Skip / Reverse / +2 各两张
            repeat(2) {
                deck.add(Card(color, CardType.SKIP))
                deck.add(Card(color, CardType.REVERSE))
                deck.add(Card(color, CardType.DRAW_TWO))
            }
            // 万用卡：Wild & +4 各四张  每个颜色1张
            deck.add(Card(color, CardType.WILD))
            deck.add(Card(color, CardType.WILD_DRAW_FOUR))
        }

        return deck.shuffled()
    }

    fun shuffle(cards: List<Card>): List<Card> = cards.shuffled()
}