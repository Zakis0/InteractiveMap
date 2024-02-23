package com.example.interactivemap

import android.graphics.Path

class CanvasPath(path: MutableList<Coords>) {
    val path = Path()

    init {
        addList(path)
    }
    private fun addInnerCoord(coords: Coords) {
        path.lineTo(coords.x, coords.y)
    }
    private fun addBorderCoord(coords: Coords) {
        path.moveTo(coords.x, coords.y)
    }
    private fun addList(list: MutableList<Coords>) {
        list.forEachIndexed { index, coords ->
            if (index == 0) {
                addBorderCoord(coords)
            }
            else {
                addInnerCoord(coords)
            }
        }
        addBorderCoord(list.first())
    }
}