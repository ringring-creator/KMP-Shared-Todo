package com.ring.ring.kmpsharedtodo

import android.app.Application
import di.initKoin
import org.koin.android.ext.koin.androidContext

class SharedTodoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin(appDeclaration = {
            androidContext(androidContext = this@SharedTodoApplication)
        }, logEnabled = BuildConfig.DEBUG)
    }
}