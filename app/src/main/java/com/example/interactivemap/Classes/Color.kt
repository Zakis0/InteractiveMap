package com.example.interactivemap.Classes

import android.graphics.Color
import java.io.Serializable

data class MyColor(var r: Int, var g: Int, var b: Int): Serializable {
    fun colorString() = "#" +
            String.format("%02x", r) +
            String.format("%02x", g) +
            String.format("%02x", b)
    fun parseColor() = Color.parseColor(colorString())
    override fun toString() = "{$r, $g, $b}"
    companion object {
        val BLACK = MyColor(0, 0, 0)
        val WHITE = MyColor(255, 255, 255)
        val GRAY = MyColor(50, 50, 50)
    }
}