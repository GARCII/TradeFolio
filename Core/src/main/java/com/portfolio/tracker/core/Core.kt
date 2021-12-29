package com.portfolio.tracker.core

import android.app.Application
import android.content.Context

object Core {
    var applicationContext: Context? = null
        private set

    lateinit var database: DatabaseManager
        private set

    fun initialize(application: Application) {
        applicationContext = application.applicationContext
        database = configureDatabase()
    }

    private fun configureDatabase(): DatabaseManager {
        applicationContext?.let {
            return DatabaseManager.getDatabase(it)
        } ?: run {
            throw Exception("You must call the initialize() method before configureDatabase()")
        }
    }
}