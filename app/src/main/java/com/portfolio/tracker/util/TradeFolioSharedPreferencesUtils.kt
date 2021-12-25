package com.portfolio.tracker.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import com.google.gson.Gson
import java.io.Serializable
import java.lang.reflect.Type
import androidx.security.crypto.MasterKeys

@SuppressLint("ApplySharedPref")
class TradeFolioSharedPreferencesUtils(context: Context) {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private var sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        "TradeFolioPreferences",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun getBoolean(key: String, defValue: Boolean) = sharedPreferences.getBoolean(key, defValue)

    fun setBoolean(key: String, value: Boolean) {
        this.sharedPreferences.edit().putBoolean(key, value).commit()
    }

    fun getString(key: String): String? = sharedPreferences.getString(key, null)

    fun setString(key: String, value: String) {
        this.sharedPreferences.edit().putString(key, value).commit()
    }

    fun getInt(key: String, defValue: Int) = this.sharedPreferences.getInt(key, defValue)

    fun setInt(key: String, value: Int) {
        this.sharedPreferences.edit().putInt(key, value).commit()
    }

    fun getFloat(key: String) = this.sharedPreferences.getFloat(key, 0.0f)

    fun setFloat(key: String, value: Float) {
        this.sharedPreferences.edit().putFloat(key, value).commit()
    }

    fun setSerializable(key: String, entity: Serializable) {
        this.sharedPreferences.edit().putString(key, Gson().toJson(entity)).commit()
    }

    fun getSerializable(key: String, clazz: Type): Serializable? {
        val gson = Gson()
        val json = this.sharedPreferences.getString(key, null)
        return if (json != null) {
            try {
                gson.fromJson(json, clazz)
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }

    fun setSerializableList(key: String, entity: List<Serializable>) {
        sharedPreferences.edit().putString(key, Gson().toJson(entity)).commit()
    }

    fun getSerializableList(key: String, clazz: Type): List<Serializable> {
        val gson = Gson()
        val json = this.sharedPreferences.getString(key, null)
        return if (json != null) {
            try {
                gson.fromJson(json, clazz)
            } catch (e: Exception) {
                listOf()
            }
        } else {
            listOf()
        }
    }

    fun contains(key: String) = sharedPreferences.contains(key)

    fun remove(key: String) {
        this.sharedPreferences.edit().remove(key).commit()
    }

    fun getLong(key: String) = sharedPreferences.getLong(key, 0)


    fun setLong(key: String, value: Long) {
        this.sharedPreferences.edit().putLong(key, value).commit()
    }
}