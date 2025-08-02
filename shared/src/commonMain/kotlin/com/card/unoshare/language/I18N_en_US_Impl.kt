package com.card.unoshare.model

/**
 * @author xinggen.guo
 * @date 02/08/2025 11:07
 * @description
 */
class I18N_en_US_Impl : I18N {
    private val P = arrayOf("YOU", "WEST", "NORTH", "EAST")
    private val C = arrayOf("NONE", "[R]RED", "[B]BLUE", "[G]GREEN", "[Y]YELLOW")

    private fun p(i: Int) = P.getOrNull(i) ?: "????"
    private fun c(i: Int) = C.getOrNull(i) ?: "????"

    override fun act_drawCard(player: Int, card: String) = "${p(player)}: Draw $card"
    override fun act_drawCardCount(player: Int, count: Int) =
        if (count == 1) "${p(player)}: Draw a card" else "${p(player)}: Draw $count cards"
    override fun act_pass(player: Int) = "${p(player)}: Pass"
    override fun act_playCard(player: Int, card: String) = "${p(player)}: $card"
    override fun act_playDraw2(from: Int, to: Int, count: Int) = "${p(from)}: Let ${p(to)} draw $count cards"
    override fun act_playRev(player: Int) = "${p(player)}: Change direction"
    override fun act_playSkip(from: Int, to: Int) =
        if (to == 0) "${p(from)}: Skip your turn" else "${p(from)}: Skip ${p(to)}'s turn"
    override fun act_playWild(player: Int, color: Int) = "${p(player)}: Change color to ${c(color)}"
    override fun act_playWildDraw4(player: Int, target: Int) = "${p(player)}: Change color & let ${p(target)} draw 4"
    override fun ask_challenge(color: Int) = "^ Do you think your previous player still has ${c(color)}?"
    override fun ask_color() = "^ Specify the following legal color"
    override fun ask_keep_play() = "^ Play the drawn card?"
    override fun ask_target() = "^ Specify the target to swap hand cards with"
    override fun btn_ask(active: Boolean) = if (active) "[Y]<ASK>" else "<ASK>"
    override fun btn_auto() = "<AUTO>"
    override fun btn_keep(active: Boolean) = if (active) "[R]<KEEP>" else "<KEEP>"
    override fun btn_load() = "[G]<LOAD>"
    override fun btn_play(active: Boolean) = if (active) "[G]<PLAY>" else "<PLAY>"
    override fun btn_save() = "[B]<SAVE>"
    override fun btn_settings(active: Boolean) = if (active) "[Y]<SETTINGS>" else "<SETTINGS>"
    override fun info_0_rotate() = "Hand cards transferred to next"
    override fun info_7_swap(from: Int, to: Int) = "${p(from)} swapped hand cards with ${p(to)}"
    override fun info_cannotDraw(player: Int, maxCards: Int) = "${p(player)} cannot hold more than $maxCards cards"
    override fun info_cannotPlay(card: String) = "Cannot play $card"
    override fun info_challenge(challenger: Int, target: Int, color: Int) =
        if (target == 0)
            "${p(challenger)} doubted that you still have ${c(color)}"
        else
            "${p(challenger)} doubted that ${p(target)} still has ${c(color)}"
    override fun info_challengeFailure(player: Int) =
        if (player == 0) "Challenge failure, you draw 6 cards" else "Challenge failure, ${p(player)} draws 6 cards"
    override fun info_challengeSuccess(player: Int) =
        if (player == 0) "Challenge success, you draw 4 cards" else "Challenge success, ${p(player)} draws 4 cards"
    override fun info_clickAgainToPlay(card: String) = "Click again to play $card"
    override fun info_dirChanged() = "Direction changed"
    override fun info_gameOver(score: Int, delta: Int): String {
        val s = if (delta < 0) "[R]($delta)[W]" else "[G](+$delta)[W]"
        return "Score: $score$s. Click UNO to restart"
    }
    override fun info_ready() = "GET READY"
    override fun info_ruleSettings() = "RULE SETTINGS"
    override fun info_save(filename: String?) =
        if (filename.isNullOrEmpty()) "Failed to save your game replay" else "Replay file saved as $filename"
    override fun info_skipped(player: Int) = "${p(player)}: Skipped"
    override fun info_welcome() = "WELCOME TO UNO CARD GAME, CLICK UNO TO START"
    override fun info_yourTurn() = "Select a card to play, or draw a card from deck"
    override fun info_yourTurn_stackDraw2(drawCount: Int, mode: Int) =
        if (mode == 1) "Stack a +2 card, or draw $drawCount cards" else "Stack a +2/+4 card, or draw $drawCount cards"
    override fun label_bgm() = "BGM"
    override fun label_forcePlay() = "When you draw a playable card:"
    override fun label_gameMode(mode: Int) = when (mode) {
        1 -> "How to play:  7-0"
        2 -> "How to play: 2vs2"
        3 -> "How to play:   3P"
        else -> "How to play:   4P"
    }
    override fun label_initialCards(count: Int) = "Initial cards: ${count / 10}${count % 10}"
    override fun label_lacks(n: Int, e: Int, w: Int, s: Int) =
        "LACK:[${char(n)}]N[${char(e)}]E[${char(w)}]W[${char(s)}]S"
    override fun label_leftArrow() = "[Y]＜－"
    override fun label_level(level: Int) = if (level == 0) "Level: EASY" else "Level: HARD"
    override fun label_no() = "NO"
    override fun label_remain_used(remaining: Int, used: Int) = "[Y]R$remaining[W]/[G]U$used"
    override fun label_rightArrow() = "[Y]＋＞"
    override fun label_score() = "SCORE"
    override fun label_snd() = "SND"
    override fun label_speed() = "SPEED"
    override fun label_stackRule(rule: Int) = when (rule) {
        0 -> "Stackable cards: NONE"
        1 -> "Stackable cards:   +2"
        else -> "Stackable cards: +2+4"
    }
    override fun label_yes() = "YES"

    private fun char(i: Int): Char = if (i in 0..4) "WRBGY"[i] else 'W'
} // I18N_en_US_Impl
