package com.portfolio.tracker.core.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.portfolio.tracker.core.entity.Holding

@Dao
interface HoldingDao {

    @Query("SELECT * FROM holding")
    fun getHoldings(): List<Holding>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(holding: Holding)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(holdings: List<Holding>)
}