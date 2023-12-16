package com.ring.ring.kmpsharedtodo

import android.app.Application
import android.content.Context
import di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.compose.BuildConfig
import org.koin.core.logger.Level
import org.koin.dsl.module

class SharedTodoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger(level = if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(androidContext = this@SharedTodoApplication)
        }
    }
}