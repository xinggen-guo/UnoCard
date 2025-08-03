package com.card.unoshare.language

/**
 * @author xinggen.guo
 * @date 02/08/2025 11:09
 * @description
 */
class I18N_ja_JP_Impl : I18N {
    private val P = arrayOf("あなた", "西", "北", "東")
    private val C = arrayOf("無色", "[R]赤色", "[B]青色", "[G]緑色", "[Y]黄色")

    private fun p(i: Int) = P.getOrNull(i) ?: "????"
    private fun c(i: Int) = C.getOrNull(i) ?: "????"

    override fun act_drawCard(player: Int, card: String) = "${p(player)}: $card[W] を引く"
    override fun act_drawCardCount(player: Int, count: Int) = "${p(player)}: 手札を $count 枚引く"
    override fun act_pass(player: Int) = "${p(player)}: パス"
    override fun act_playCard(player: Int, card: String) = "${p(player)}: $card"
    override fun act_playCard(player: Int) = "${p(player)}"
    override fun act_playDraw2(from: Int, to: Int, count: Int) = "${p(from)}: ${p(to)}に手札を $count 枚引かせる"
    override fun act_playRev(player: Int) = "${p(player)}: 方向を変える"
    override fun act_playSkip(from: Int, to: Int) = "${p(from)}: ${p(to)}の番をスキップ"
    override fun act_playWild(player: Int, color: Int) = "${p(player)}: 次の色を${c(color)}[W]に変える"
    override fun act_playWildDraw4(player: Int, target: Int) = "${p(player)}: 色を変更 & ${p(target)}に手札を 4 枚引かせる"
    override fun ask_challenge(color: Int) = "^ 前の方はまだ${c(color)}の手札[W]を持っていると思いますか?"
    override fun ask_color() = "^ 次の色を指定してください"
    override fun ask_keep_play() = "^ 引いたカードすぐを出しますか?"
    override fun ask_target() = "^ 手札を交換する相手を指定してください"
    override fun btn_ask(active: Boolean) = if (active) "[Y]<任意>" else "<任意>"
    override fun btn_auto() = "<オート>"
    override fun btn_keep(active: Boolean) = if (active) "[R]<保留>" else "<保留>"
    override fun btn_load() = "[G]<読取>"
    override fun btn_play(active: Boolean) = if (active) "[G]<出す>" else "<出す>"
    override fun btn_save() = "[B]<保存>"
    override fun btn_settings(active: Boolean) = if (active) "[Y]<設定>" else "<設定>"
    override fun info_0_rotate() = "手札を次の方に転送しました"
    override fun info_7_swap(from: Int, to: Int) = "${p(from)}は${p(to)}と手札を交換しました"
    override fun info_cannotDraw(player: Int, maxCards: Int) = "${p(player)}は手札を $maxCards 枚以上持てません"
    override fun info_cannotPlay(card: String) = "$card[W] を出せません"
    override fun info_challenge(challenger: Int, target: Int, color: Int) = "${p(challenger)}は${p(target)}が${c(color)}の手札[W]を持っていると思う"
    override fun info_challengeFailure(player: Int) = "チャレンジ失敗、${p(player)}は手札を 6 枚引く"
    override fun info_challengeSuccess(player: Int) = "チャレンジ成功、${p(player)}は手札を 4 枚引く"
    override fun info_clickAgainToPlay(card: String) = "もう一度クリックして $card[W] を出す"
    override fun info_dirChanged() = "方向が変わりました"
    override fun info_gameOver(score: Int, delta: Int): String {
        val s = if (delta < 0) "[R]($delta)[W]" else "[G](+$delta)[W]"
        return "スコア: $score$s. UNO をクリックして再開"
    }
    override fun info_ready() = "準備完了"
    override fun info_ruleSettings() = "ルール設定"
    override fun info_save(filename: String?) =
        if (filename.isNullOrEmpty()) "リプレイファイルは保存できませんでした"
        else "リプレイファイルは $filename として保存しました"
    override fun info_skipped(player: Int) = "${p(player)}の番はスキップされました"
    override fun info_welcome() = "UNO へようこそ! UNO をクリックしてゲームスタート"
    override fun info_yourTurn() = "手札を一枚出すか、デッキから手札を一枚引く"
    override fun info_yourTurn_stackDraw2(drawCount: Int, mode: Int) =
        if (mode == 1) "+2 を一枚重ねるか、デッキから手札を $drawCount 枚引く"
        else "+2/+4 を一枚重ねるか、デッキから手札を $drawCount 枚引く"
    override fun label_bgm() = "音楽"
    override fun label_forcePlay() = "出せる手札を引いた時:"
    override fun label_gameMode(mode: Int) = when (mode) {
        1 -> "遊び方:  7-0"
        2 -> "遊び方: 2vs2"
        3 -> "遊び方:   3P"
        else -> "遊び方:   4P"
    }
    override fun label_initialCards(count: Int) = "最初の手札数: ${count / 10}${count % 10}"
    override fun label_lacks(n: Int, e: Int, w: Int, s: Int) =
        "欠色:[${char(n)}]N[${char(e)}]E[${char(w)}]W[${char(s)}]S"
    override fun label_leftArrow() = "[Y]＜－"
    override fun label_level(level: Int) = if (level == 0) "難易度: 　簡単" else "難易度: 難しい"
    override fun label_no() = "いいえ"
    override fun label_remain_used(remaining: Int, used: Int) = "[Y]残$remaining[G]使$used"
    override fun label_rightArrow() = "[Y]＋＞"
    override fun label_score() = "スコア"
    override fun label_snd() = "音声"
    override fun label_speed() = "速さ"
    override fun label_stackRule(rule: Int) = when (rule) {
        0 -> "積み重ね可: 　　なし"
        1 -> "積み重ね可: ＋２のみ"
        else -> "積み重ね可: ＋２＋４"
    }
    override fun label_yes() = "はい"

    private fun char(i: Int): Char = if (i in 0..4) "WRBGY"[i] else 'W'
} // I18N_ja_JP_Impl