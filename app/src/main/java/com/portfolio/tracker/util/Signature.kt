package com.portfolio.tracker.util

import java.lang.Exception
import java.lang.RuntimeException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object Signature {
    private const val HMAC_SHA256 = "HmacSHA256"

    private fun ByteArray.toHex(): String {
        val hexChars = "0123456789abcdef".toCharArray()
        val hex = CharArray(2 * this.size)
        this.forEachIndexed { i, byte ->
            val unsigned = 0xff and byte.toInt()
            hex[2 * i] = hexChars[unsigned / 16]
            hex[2 * i + 1] = hexChars[unsigned % 16]
        }
        return hex.joinToString("")
    }

    fun getSignature(data: String, key: String): String {
        val hMacSha256: ByteArray = try {
            val secretKeySpec = SecretKeySpec(key.toByteArray(), HMAC_SHA256)
            val mac = Mac.getInstance(HMAC_SHA256)
            mac.init(secretKeySpec)
            mac.doFinal(data.toByteArray())
        } catch (e: Exception) {
            throw RuntimeException("Failed to calculate hmac-sha256", e)
        }
        return hMacSha256.toHex()
    }
}