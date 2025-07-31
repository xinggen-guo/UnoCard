package com.card.unoshare

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform