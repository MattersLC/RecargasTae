package org.mini

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.context.startKoin
import org.mini.di.appModule

fun MainViewController() = ComposeUIViewController { App(CrossConfigDevice()) }

fun initKoin(){
    startKoin{
        modules(appModule())
    }.koin
}