package com.ring.ring.kmpsharedtodo

import com.ring.ring.kmpsharedtodo.di.initKoin
import org.koin.core.KoinApplication

fun KoinApplication.Companion.start(logEnabled: Boolean): KoinApplication = initKoin(
    appDeclaration = { },
    logEnabled = logEnabled
)