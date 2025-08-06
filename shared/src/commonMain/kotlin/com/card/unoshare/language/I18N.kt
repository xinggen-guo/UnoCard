package com.card.unoshare.language

/**
 * @author xinggen.guo
 * @date 02/08/2025 11:06
 * @description
 */
interface I18N {
    fun act_drawCard(player: Int, card: String): String
    fun act_drawCardCount(player: Int, count: Int): String
    fun act_pass(player: Int): String
    fun act_playCard(player: Int, card: String): String
    fun act_playCard(player: Int): String
    fun act_playDraw2(from: Int, to: Int, count: Int): String
    fun act_playRev(player: Int): String
    fun act_playSkip(from: Int, to: Int): String
    fun act_playWild(player: Int, color: Int): String
    fun act_playWildDraw4(player: Int, target: Int): String
    fun ask_challenge(color: Int): String
    fun ask_color(): String
    fun ask_keep_play(): String
    fun ask_target(): String
    fun btn_ask(active: Boolean): String
    fun btn_auto(): String
    fun btn_keep(active: Boolean): String
    fun btn_load(): String
    fun btn_play(active: Boolean): String
    fun btn_save(): String
    fun btn_settings(active: Boolean): String
    fun info_0_rotate(): String
    fun info_7_swap(from: Int, to: Int): String
    fun info_cannotDraw(player: Int, maxCards: Int): String
    fun info_cannotPlay(card: String): String
    fun info_challenge(challenger: Int, target: Int, color: Int): String
    fun info_challengeFailure(player: Int): String
    fun info_challengeSuccess(player: Int): String
    fun info_clickAgainToPlay(card: String): String
    fun info_dirChanged(): String
    fun info_gameOver(): String
    fun info_ready(): String
    fun info_ruleSettings(): String
    fun info_save(filename: String?): String
    fun info_skipped(player: Int): String
    fun info_welcome(): String
    fun info_yourTurn(): String
    fun info_yourTurn_stackDraw2(drawCount: Int, mode: Int): String
    fun label_bgm(): String
    fun label_forcePlay(): String
    fun label_gameMode(mode: Int): String
    fun label_initialCards(count: Int): String
    fun label_lacks(n: Int, e: Int, w: Int, s: Int): String
    fun label_leftArrow(): String
    fun label_level(level: Int): String
    fun label_no(): String
    fun label_remain_used(remaining: Int, used: Int): String
    fun label_rightArrow(): String
    fun label_score(): String
    fun label_snd(): String
    fun label_speed(): String
    fun label_stackRule(rule: Int): String
    fun label_yes(): String

    companion object {
        val EN_US: I18N = I18N_en_US_Impl()
        val ZH_CN: I18N = I18N_zh_CN_Impl()
        val JA_JP: I18N = I18N_ja_JP_Impl()
    }
}
