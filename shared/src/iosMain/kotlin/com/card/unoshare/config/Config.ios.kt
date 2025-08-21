package com.card.unoshare.config

import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
actual val IS_RELEASE_BUILD: Boolean = !Platform.isDebugBinary