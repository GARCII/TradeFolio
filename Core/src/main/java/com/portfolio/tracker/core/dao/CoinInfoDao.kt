package com.portfolio.tracker.core.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.portfolio.tracker.core.entity.CoinInfo

@Dao
interface CoinInfoDao {
    @Query("SELECT * FROM CoinInfo")
    fun getCoinsInfo(): List<CoinInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coinInfo: CoinInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coinsInfo: List<CoinInfo>)
}