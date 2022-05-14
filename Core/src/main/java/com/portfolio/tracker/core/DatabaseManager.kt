package com.portfolio.tracker.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.portfolio.tracker.core.dao.CoinInfoDao
import com.portfolio.tracker.core.dao.HoldingDao
import com.portfolio.tracker.core.entity.*

@Database(
    version = 1,
    entities = [
        Holding::class,
        CoinInfo::class,
        Bag::class,
        Portfolio::class,

    ],
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DatabaseManager : RoomDatabase() {

    abstract fun holdingDao(): HoldingDao
    abstract fun coinInfoDao(): CoinInfoDao

    companion object {
        private var INSTANCE: DatabaseManager? = null
        fun getDatabase(context: Context): DatabaseManager {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseManager::class.java,
                    "TradeFolio"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}