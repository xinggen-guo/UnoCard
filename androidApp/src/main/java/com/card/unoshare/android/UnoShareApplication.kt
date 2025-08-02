package com.card.unoshare.android

import android.app.Application
import com.card.unoshare.ObjectUnoCommon

/**
 * @author xinggen.guo
 * @date 01/08/2025 21:13
 * @description
 */
class UnoShareApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectUnoCommon.init(this)
    }
}