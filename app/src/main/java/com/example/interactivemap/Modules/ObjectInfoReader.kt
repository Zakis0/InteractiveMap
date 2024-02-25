package com.example.interactivemap.Modules

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import com.example.interactivemap.Activities.MapActivity
import com.example.interactivemap.Activities.ObjectInfoActivity
import com.example.interactivemap.Classes.DataType
import com.example.interactivemap.Classes.InteractiveObject
import com.example.interactivemap.Constants.Keys


object ObjectInfoReader {
    private const val DATA_TYPE_FIELD_NAME = "Type"
    private const val OBJECT_NAME_FIELD_NAME = "Name"

    private const val TEXT_SIZE = 20f

    fun createViewsByObjectsInfo(activity: ObjectInfoActivity) {
        val interactiveObject = activity.interactiveObject!!
        addTextView(activity, DATA_TYPE_FIELD_NAME, interactiveObject.dataType)
        addTextView(activity, OBJECT_NAME_FIELD_NAME, interactiveObject.name)

        interactiveObject.listOfData.forEach { map ->
            map.keys.forEach { key ->
                when {
                    activity.dataTypesMap!![interactiveObject.dataType]!!.ints.contains(key) -> {
                        addTextView(
                            activity,
                            key,
                            interactiveObject.ints[key]!!.toString()
                        )
                    }
                    activity.dataTypesMap!![interactiveObject.dataType]!!.doubles.contains(key) -> {
                        addTextView(
                            activity,
                            key,
                            interactiveObject.doubles[key]!!.toString()
                        )
                    }
                    activity.dataTypesMap!![interactiveObject.dataType]!!.bools.contains(key) -> {
                        addSwitch(
                            activity,
                            key,
                            interactiveObject.bools[key]!!
                        )
                    }
                    activity.dataTypesMap!![interactiveObject.dataType]!!.strings.contains(key) -> {
                        addTextView(
                            activity,
                            key,
                            interactiveObject.strings[key]!!.toString()
                        )
                    }
                    activity.dataTypesMap!![interactiveObject.dataType]!!.bigStrings.contains(key) -> {
                        addTextView(
                            activity,
                            key,
                            interactiveObject.bigStrings[key]!!.toString()
                        )
                    }
                }
            }
        }
    }
    fun openObjectInfo(interactiveObject: InteractiveObject, activity: MapActivity?) {
        if (interactiveObject.dataType != DataType.NULL_DATA_TYPE_NAME) {
            launchIntent(
                activity!!.activityLauncher!!,
                activity,
                interactiveObject,
                activity.dataTypesMap!!
            )
        }
    }
    private fun launchIntent(
        activityLauncher: ActivityResultLauncher<Intent>,
        context: Context,
        interactiveObject: InteractiveObject,
        dataTypesMap: MutableMap<String, DataType>
    ) {
        Intent(context, ObjectInfoActivity::class.java).apply {
            putExtra(Keys.DATA_TYPES_MAP_KEY, HashMap(dataTypesMap))
            putExtra(Keys.INTERACTIVE_OBJECT_INFO_KEY, interactiveObject)
            activityLauncher.launch(this)
        }
    }
    private fun addTextView(
        activity: ObjectInfoActivity,
        name: String,
        text: String
    ) {
        TextView(activity).apply {
            this.text = "$name: $text"
            textSize = TEXT_SIZE
            this.layoutParams = LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply { gravity = Gravity.CENTER }
            activity.binding.infoLinearLayout.addView(this)
        }
    }
    private fun addSwitch(
        activity: ObjectInfoActivity,
        name: String,
        isChecked: Boolean
    ) {
        Switch(activity).apply {
            this.text = name
            textSize = TEXT_SIZE
            isEnabled = false
            this.isChecked = isChecked
            this.layoutParams = LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply { gravity = Gravity.CENTER }
            activity.binding.infoLinearLayout.addView(this)
        }
    }
}