package org.mini

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform