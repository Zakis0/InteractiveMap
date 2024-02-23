package com.example.interactivemap

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast

class MapView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint: Paint = Paint()
    var activity: MainActivity? = null

    var objects: MutableList<InteractiveObject> = mutableListOf()

    init {
        paint.color = Color.BLUE
    }

    override fun onDraw(canvas: Canvas) {
        objects.forEach { obj ->
            canvas.drawPath(obj.getCanvasPath().path, paint)
        }
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchCords = Coords(event.x, event.y)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}