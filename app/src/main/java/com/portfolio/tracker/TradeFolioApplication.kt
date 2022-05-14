package com.portfolio.tracker

import android.app.Application
import com.portfolio.tracker.core.Core

class TradeFolioApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Core.initialize(this)
    }
}