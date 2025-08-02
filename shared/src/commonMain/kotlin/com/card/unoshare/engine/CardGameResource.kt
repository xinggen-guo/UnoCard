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

    var bgWelcome: String? = null

    var bgClockwise: String? = null

    var bgCounter:String? = null

    var startOrRefresh: String? = null

    init {
        bgWelcome = "files/bg_welcome.png"
        bgClockwise = "files/bg_clockwise.png"
        bgCounter = "files/bg_counter.png"
        startOrRefresh = "files/back.png"
        cards.addAll(CardShuffler.createDeck().shuffled())
    }

    @OptIn(ExperimentalResourceApi::class)
    suspend fun getBgWelComeImage():ImageBitmap{
        return Res.readBytes(bgWelcome?:"").toImageBitmap()
    }

}