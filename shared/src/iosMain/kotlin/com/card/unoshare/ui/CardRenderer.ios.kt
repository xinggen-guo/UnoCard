package com.card.unoshare.ui

import com.card.unoshare.model.Card

/**
 * @author xinggen.guo
 * @date 01/08/2025 11:42
 * @description General rendering interface for UNO visuals
 */

actual object CardRenderer {
    /**
     * Render full game background
     */
    actual fun renderBackground(): ByteArray {
        TODO("Not yet implemented")
    }

    /**
     * Render a single card image
     */
    actual fun renderCard(card: Card): ByteArray {
        TODO("Not yet implemented")
    }

    /**
     * Render a player's hand zone
     */
    actual fun renderHand(
        cards: List<Card>,
        playerName: String
    ): ByteArray {
        TODO("Not yet implemented")
    }

    /**
     * Render draw pile / discard pile visuals
     */
    actual fun renderDeck(
        drawCount: Int,
        discardTop: Card?
    ): ByteArray {
        TODO("Not yet implemented")
    }

    /**
     * Render a text banner or info line
     */
    actual fun renderTextInfo(text: String): ByteArray {
        TODO("Not yet implemented")
    }

}