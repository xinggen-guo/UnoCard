package com.card.unoshare.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.card.unoshare.model.CardColor

/**
 * @author xinggen.guo
 * @date 03/08/2025 21:35
 * @description
 */
object ColorSelectorDialogController {
    private var _visible by mutableStateOf(false)
    private var _onSelect: ((CardColor) -> Unit)? = null

    fun show(onSelect: (CardColor) -> Unit) {
        _onSelect = onSelect
        _visible = true
    }

    fun dismiss() {
        _visible = false
    }

    val isVisible: Boolean get() = _visible

    fun handleSelection(color: CardColor) {
        _onSelect?.invoke(color)
        dismiss()
    }
}