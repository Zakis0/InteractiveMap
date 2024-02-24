package com.example.interactivemap.Classes

import kotlin.math.pow
import kotlin.math.sqrt

data class Coords(var x: Float = 0f, var y: Float = 0f) {
    operator fun plus(otherCords: Coords) = Coords(x + otherCords.x, y + otherCords.y)
    operator fun minus(otherCords: Coords) = Coords(x - otherCords.x, y - otherCords.y)
    fun getDistant(otherCords: Coords) = sqrt((x - otherCords.x).pow(2) + (y - otherCords.y).pow(2))
    override fun toString() = "[$x, $y]"
}