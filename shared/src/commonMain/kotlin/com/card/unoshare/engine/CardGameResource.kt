package com.card.unoshare.engine

import androidx.compose.ui.graphics.ImageBitmap
import com.card.unoshare.model.Card
import com.card.unoshare.language.I18N
import com.card.unoshare.util.CardShuffler
import com.card.unoshare.util.toImageBitmap
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.ResourceEnvironment
import unocard.shared.generated.resources.Res

/**
 * @author xinggen.guo
 * @date 01/08/2025 15:47
 * @description
 */
object CardGameResource {

    val cards = mutableListOf<Card>()

    private var bgWelcome: String = "files/bg_welcome.png"

    private var bgClockwise: String = "files/icon_clockwise.png"

    private var bgCounter:String = "files/icon_counter.png"

    private var victoryPic: String = "files/victory.png"

    val i18n: I18N by lazy {
        //todo
        val locale = "en"
        when {
            locale.contains("zh", ignoreCase = true) -> I18N.ZH_CN
            locale.contains("ja", ignoreCase = true) -> I18N.JA_JP
            else -> I18N.EN_US
        }
    }

    init {
        cards.addAll(CardShuffler.createDeck().shuffled())
    }

    @OptIn(ExperimentalResourceApi::class)
    suspend fun getBgWelComeImage():ImageBitmap{
        return Res.readBytes(bgWelcome).toImageBitmap()
    }

    @OptIn(ExperimentalResourceApi::class)
    suspend fun getClockWise(): ImageBitmap {
        return Res.readBytes(bgClockwise).toImageBitmap()
    }

    @OptIn(ExperimentalResourceApi::class)
    suspend fun getCounterWise(): ImageBitmap {
        return Res.readBytes(bgCounter).toImageBitmap()
    }

    @OptIn(ExperimentalResourceApi::class)
    suspend fun getVictoryPic(): ImageBitmap {
        return Res.readBytes(victoryPic).toImageBitmap()
    }
}