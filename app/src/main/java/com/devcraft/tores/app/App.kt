package com.devcraft.tores.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.devcraft.tores.app.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                viewModelModule,
                netModule,
                preferencesModule,
                netApiModule,
                repositoryModule,
                repositoryMappersModule,
                utilsModules
            )
        }
        instance = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}