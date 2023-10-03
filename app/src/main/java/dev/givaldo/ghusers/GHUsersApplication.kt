package dev.givaldo.ghusers

import android.app.Application
import android.content.Context

class GHUsersApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        GHUsersApplication.applicationContext = this
    }

    companion object {
        lateinit var applicationContext: Context
    }
}