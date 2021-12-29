package com.portfolio.tracker.core

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
/**
 * Created by mohamed on 07/03/2019.
 */
object Converters {

    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?): Date? = if (null == value) null else Date(value)

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?): Long? = date?.time

    @TypeConverter
    @JvmStatic
    fun stringToDoubleArray(value: String): List<Double>? {
        val type = object : TypeToken<List<Double>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    @JvmStatic
    fun doubleArrayToString(list: List<Double>?): String? = Gson().toJson(list)

    @TypeConverter
    @JvmStatic
    fun stringToIntArray(value: String?): List<Int>? {
        val type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    @JvmStatic
    fun stringArrayToString(list: List<String>?): String? = Gson().toJson(list)

    @TypeConverter
    @JvmStatic
    fun stringToStringArray(value: String): List<String>? {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    @JvmStatic
    fun intArrayToString(list: List<Int>?): String? = Gson().toJson(list)



    @TypeConverter
    @JvmStatic
    fun stringToMap(value: String): Map<String, String>? {
        val type = object : TypeToken<Map<String, String>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    @JvmStatic
    fun mapToString(value: Map<String, String>?): String {
        return if (value == null) "" else Gson().toJson(value)
    }
}