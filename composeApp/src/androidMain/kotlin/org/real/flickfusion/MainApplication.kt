package org.real.flickfusion

import android.app.Application

/**
 * @author Frank Shao
 * @created 11/06/2024
 * Description:
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

       /* startKoin {
            printLogger(Level.DEBUG)
            androidContext(this@MainApplication)
            modules(NetworkModule, PersistModule, UseCaseModule, RepoModule)
        }*/
    }
}