package com.card.unoshare.engine

import androidx.compose.ui.graphics.ImageBitmap
import com.card.unoshare.model.Card
import com.card.unoshare.util.CardShuffler
import com.card.unoshare.util.toImageBitmap
import org.jetbrains.compose.resources.ExperimentalResourceApi
import unocard.shared.generated.resources.Res

/**
 * @author xinggen.guo
 * @date 01/08/2025 15:47
 * @description
 */
object CardGameResource {

    val cards = mutableListOf<Card>()

    private var bgWelcome: String = "files/images/bg_welcome.png"

    private var bgClockwise: String = "files/images/icon_clockwise.png"

    private var bgCounter:String = "files/images/icon_counter.png"

    private var victoryPic: String = "files/images/victory.png"

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