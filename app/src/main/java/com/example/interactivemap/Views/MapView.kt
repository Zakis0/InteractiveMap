package com.example.interactivemap.Views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Region
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.interactivemap.Activities.MapActivity
import com.example.interactivemap.Classes.ClickModes
import com.example.interactivemap.Classes.Coords
import com.example.interactivemap.Classes.InteractiveObject
import com.example.interactivemap.Modules.ObjectInfoReader
import com.example.interactivemap.Modules.ObjectOpener

class MapView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint: Paint = Paint()
    var activity: MapActivity? = null

    var clickMode: ClickModes? = null

    var objects: MutableList<InteractiveObject> = mutableListOf()

    override fun onDraw(canvas: Canvas) {
        objects.forEach { obj ->
            canvas.drawPath(obj.canvasPath.path, paint.apply {
                color = obj.color.parseColor()
            })
        }
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchCords = Coords(event.x, event.y)
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                val touchedObject = getTouchedObject(touchCords)
                if (touchedObject != null) {
                    when(clickMode) {
                        ClickModes.OPEN_INSIDE_OBJECT -> {
                            ObjectOpener.openInsideObject(touchedObject, activity)
                        }
                        ClickModes.OPEN_INFO -> {
                            ObjectInfoReader.openObjectInfo(touchedObject, activity)
                        }
                        else -> {

                        }
                    }
                }
            }
        }
        return true
    }
    private fun getTouchedObject(coords: Coords): InteractiveObject? {
        objects.forEach { obj ->
            if (isPointInsidePath(coords, obj.canvasPath.path)) {
                return obj
            }
        }
        return null
    }
    private fun isPointInsidePath(coords: Coords, path: Path): Boolean {
        val region = Region()
        val bounds = RectF()
        path.computeBounds(bounds, true)
        region.setPath(path,
            Region(
                bounds.left.toInt(),
                bounds.top.toInt(),
                bounds.right.toInt(),
                bounds.bottom.toInt()
            )
        )
        return region.contains(coords.x.toInt(), coords.y.toInt())
    }
}