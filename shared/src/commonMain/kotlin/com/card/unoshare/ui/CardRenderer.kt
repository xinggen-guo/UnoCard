package com.card.unoshare.ui

import com.card.unoshare.model.Card

/**
 * @author xinggen.guo
 * @date 01/08/2025 11:42
 * @description General rendering interface for UNO visuals
 */

expect object CardRenderer {
    /**
     * Render full game background
     */
    fun renderBackground(): ByteArray

    /**
     * Render a single card image
     */
    fun renderCard(card: Card): ByteArray

    /**
     * Render a player's hand zone
     */
    fun renderHand(cards: List<Card>, playerName: String): ByteArray

    /**
     * Render draw pile / discard pile visuals
     */
    fun renderDeck(drawCount: Int, discardTop: Card?): ByteArray

    /**
     * Render a text banner or info line
     */
    fun renderTextInfo(text: String): ByteArray
}