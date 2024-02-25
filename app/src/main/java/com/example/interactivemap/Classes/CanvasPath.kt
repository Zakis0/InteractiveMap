package com.example.interactivemap.Classes

import android.graphics.Path
import java.io.Serializable

class CanvasPath(coordsList: MutableList<Coords>): Serializable {
    @Transient // to do not serialize Path()
    val path = Path()

    init {
        addList(coordsList)
    }
    private fun addList(list: MutableList<Coords>) {
        list.forEachIndexed { index, coords ->
            if (index == 0) {
                path.moveTo(coords.x, coords.y)
            }
            else {
                path.lineTo(coords.x, coords.y)
            }
        }
    }
}