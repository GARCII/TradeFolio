package com.portfolio.tracker.util

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.graphics.drawable.DrawableCompat
import java.text.DecimalFormat

fun Double.formatDecimal() = when {
    this < 1 -> "#.####"
    this <= 100 -> "#.##"
    this <= 500 -> "#.#"
    else -> "#"

}.let {
    "${DecimalFormat(it).format(this)} $"
}

fun Drawable.tintDrawable(color: Int) {
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> this.mutate().colorFilter =
            BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        else -> DrawableCompat.setTint(this, color)
    }
}