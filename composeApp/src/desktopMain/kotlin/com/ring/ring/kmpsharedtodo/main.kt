package com.ring.ring.kmpsharedtodo

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.ring.ring.kmpsharedtodo.di.initKoin
import com.ring.ring.kmpsharedtodo.ui.App
import org.koin.core.Koin

lateinit var koin: Koin

fun main() {
    koin = initKoin(logEnabled = true).koin
    application {
        Window(onCloseRequest = ::exitApplication, title = "KMP-Shared-Todo") {
            App()
        }
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    App()
}