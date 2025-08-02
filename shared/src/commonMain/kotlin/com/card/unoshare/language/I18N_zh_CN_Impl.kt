package com.card.unoshare.model

/**
 * @author xinggen.guo
 * @date 02/08/2025 11:09
 * @description
 */
class I18N_zh_CN_Impl : I18N {
    private val P = arrayOf("你", "西家", "北家", "东家")
    private val C = arrayOf("无色", "[R]红色", "[B]蓝色", "[G]绿色", "[Y]黄色")

    private fun p(i: Int) = P.getOrNull(i) ?: "????"
    private fun c(i: Int) = C.getOrNull(i) ?: "????"

    override fun act_drawCard(player: Int, card: String) = "${p(player)}: 摸到 $card"
    override fun act_drawCardCount(player: Int, count: Int) = "${p(player)}: 摸 $count 张牌"
    override fun act_pass(player: Int) = "${p(player)}: 过牌"
    override fun act_playCard(player: Int, card: String) = "${p(player)}: $card"
    override fun act_playDraw2(from: Int, to: Int, count: Int) = "${p(from)}: 令${p(to)}摸 $count 张牌"
    override fun act_playRev(player: Int) = "${p(player)}: 改变方向"
    override fun act_playSkip(from: Int, to: Int) = "${p(from)}: 跳过${p(to)}的回合"
    override fun act_playWild(player: Int, color: Int) = "${p(player)}: 将接下来的合法颜色改为${c(color)}"
    override fun act_playWildDraw4(player: Int, target: Int) = "${p(player)}: 变色 & 令${p(target)}摸 4 张牌"
    override fun ask_challenge(color: Int) = "^ 你是否认为你的上家仍有${c(color)}牌?"
    override fun ask_color() = "^ 指定接下来的合法颜色"
    override fun ask_keep_play() = "^ 是否打出摸到的牌?"
    override fun ask_target() = "^ 指定换牌目标"
    override fun btn_ask(active: Boolean) = if (active) "[Y]<可选>" else "<可选>"
    override fun btn_auto() = "<托管>"
    override fun btn_keep(active: Boolean) = if (active) "[R]<保留>" else "<保留>"
    override fun btn_load() = "[G]<读取>"
    override fun btn_play(active: Boolean) = if (active) "[G]<打出>" else "<打出>"
    override fun btn_save() = "[B]<保存>"
    override fun btn_settings(active: Boolean) = if (active) "[Y]<设置>" else "<设置>"
    override fun info_0_rotate() = "所有人将牌传给下家"
    override fun info_7_swap(from: Int, to: Int) = "${p(from)}和${p(to)}换牌"
    override fun info_cannotDraw(player: Int, maxCards: Int) = "${p(player)}最多保留 $maxCards 张牌"
    override fun info_cannotPlay(card: String) = "无法打出 $card"
    override fun info_challenge(challenger: Int, target: Int, color: Int) = "${p(challenger)}认为${p(target)}仍有${c(color)}牌"
    override fun info_challengeFailure(player: Int) = "挑战失败, ${p(player)}摸 6 张牌"
    override fun info_challengeSuccess(player: Int) = "挑战成功, ${p(player)}摸 4 张牌"
    override fun info_clickAgainToPlay(card: String) = "再次点击以打出 $card"
    override fun info_dirChanged() = "方向已改变"
    override fun info_gameOver(score: Int, delta: Int): String {
        val s = if (delta < 0) "[G]($delta)[W]" else "[R](+$delta)[W]"
        return "你的分数为 $score$s, 点击 UNO 重新开始游戏"
    }
    override fun info_ready() = "准备"
    override fun info_ruleSettings() = "规则设置"
    override fun info_save(filename: String?) =
        if (filename.isNullOrEmpty()) "回放文件保存失败" else "回放文件已保存为 $filename"
    override fun info_skipped(player: Int) = "${p(player)}: 被跳过"
    override fun info_welcome() = "欢迎来到 UNO, 点击 UNO 开始游戏"
    override fun info_yourTurn() = "选择一张牌打出, 或从发牌堆摸一张牌"
    override fun info_yourTurn_stackDraw2(drawCount: Int, mode: Int) =
        if (mode == 1) "叠加一张 +2, 或从发牌堆摸 $drawCount 张牌"
        else "叠加一张 +2/+4, 或从发牌堆摸 $drawCount 张牌"
    override fun label_bgm() = "音乐"
    override fun label_forcePlay() = "摸到可出的牌时, 是否打出:"
    override fun label_gameMode(mode: Int) = when (mode) {
        1 -> "玩法:  7-0"
        2 -> "玩法: 2vs2"
        3 -> "玩法:   3P"
        else -> "玩法:   4P"
    }
    override fun label_initialCards(count: Int) = "发牌张数: ${count / 10}${count % 10}"
    override fun label_lacks(n: Int, e: Int, w: Int, s: Int) =
        "缺色:[${char(n)}]N[${char(e)}]E[${char(w)}]W[${char(s)}]S"
    override fun label_leftArrow() = "[Y]＜－"
    override fun label_level(level: Int) = if (level == 0) "难易度: 简单" else "难易度: 困难"
    override fun label_no() = "否"
    override fun label_remain_used(remaining: Int, used: Int) = "[Y]剩$remaining[G]用$used"
    override fun label_rightArrow() = "[Y]＋＞"
    override fun label_score() = "分数"
    override fun label_snd() = "音效"
    override fun label_speed() = "速度"
    override fun label_stackRule(rule: Int) = when (rule) {
        0 -> "允许叠牌: 　　　无"
        1 -> "允许叠牌: 只有＋２"
        else -> "允许叠牌: ＋２＋４"
    }
    override fun label_yes() = "是"

    private fun char(i: Int): Char = if (i in 0..4) "WRBGY"[i] else 'W'
} // I18N_zh_CN_Impl