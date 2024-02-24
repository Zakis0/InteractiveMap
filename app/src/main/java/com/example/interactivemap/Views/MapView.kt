package com.example.interactivemap.Views

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.interactivemap.Activities.MapActivity
import com.example.interactivemap.Classes.Coords
import com.example.interactivemap.Classes.InteractiveObject
import com.example.interactivemap.Constants.Debug
import com.example.interactivemap.Constants.Keys
import com.example.interactivemap.Modules.JSONParser

class MapView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint: Paint = Paint()
    var activity: MapActivity? = null

    var objects: MutableList<InteractiveObject> = mutableListOf()

    override fun onDraw(canvas: Canvas) {
        objects.forEach { obj ->
            canvas.drawPath(obj.getCanvasPath().path, paint.apply {
                color = obj.color.parseColor()
            })
        }
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchCords = Coords(event.x, event.y)
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                val touchedIndex = getTouchedFigureIndex(touchCords.x, touchCords.y)
                if (touchedIndex != -1 && objects[touchedIndex].insideObject != "") {
                    Log.d(Debug.MAP_OPENER_DEBUG,
                        "Open object: ${objects[touchedIndex].insideObject}.json")

                    val intent = Intent(activity, MapActivity::class.java)

                    intent.putExtra(Keys.MAP_NAME_KEY, activity!!.mapName)
                    intent.putExtra(Keys.MAP_OBJECT_JSON_KEY, objects[touchedIndex].insideObject)
                    intent.putExtra(Keys.DATA_TYPES_MAP_KEY, HashMap(activity!!.dataTypesMap))

                    activity!!.activityLauncher!!.launch(intent)
                }
            }
        }
        return true
    }
    private fun getTouchedFigureIndex(x: Float, y: Float): Int {
        for (index in objects.indices) {
            val bounds = RectF()
            objects[index].getCanvasPath().path.computeBounds(bounds, true)
            if (bounds.contains(x, y)) {
                return index
            }
        }
        return -1
    }
}