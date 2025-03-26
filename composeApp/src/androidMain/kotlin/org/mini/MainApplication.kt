package org.mini;

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.mini.di.appModule

//import org.example.project.data.DatabaseDriverFactory


class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication) // Configuración del contexto de Android
            androidLogger() // Logger para depurar Koin
            modules(appModule()) // Registrar el módulo directamente
        }
    }
}

