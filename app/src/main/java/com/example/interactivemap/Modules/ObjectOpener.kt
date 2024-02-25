package com.example.interactivemap.Modules

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.example.interactivemap.Activities.MapActivity
import com.example.interactivemap.Classes.ClickModes
import com.example.interactivemap.Classes.Constants
import com.example.interactivemap.Classes.DataType
import com.example.interactivemap.Classes.InteractiveObject
import com.example.interactivemap.Constants.Debug
import com.example.interactivemap.Constants.Errors
import com.example.interactivemap.Constants.Keys

object ObjectOpener {
    fun openMapMain(
        context: Context,
        mapName: String,
        activityLauncher: ActivityResultLauncher<Intent>
    ) {
        val dataTypesMap =
            JSONParser.getDataTypesMap(
                context,
                "$mapName/${JSONParser.DATA_TYPE_JSON_PATH}"
            )
        launchIntent(
            activityLauncher,
            context,
            mapName,
            JSONParser.MAIN_JSON_PATH,
            dataTypesMap
        )
    }
    fun openInsideObject(interactiveObject: InteractiveObject, activity: MapActivity?) {
        if (interactiveObject.insideObject != JSONParser.NULL_INSIDE_OBJECT) {
            Log.d(
                Debug.MAP_OPENER_DEBUG,
                "Open object: ${interactiveObject.insideObject}.json"
            )
            launchIntent(
                activity!!.activityLauncher!!,
                activity,
                activity.mapName!!,
                interactiveObject.insideObject,
                activity.dataTypesMap!!
            )
        }
    }
    fun checkIfInputIsNull(
        mapName: String?,
        objectJsonName: String?,
        dataTypesMap: MutableMap<String, DataType>?
    ) {
        if (mapName.isNullOrEmpty()) {
            Log.e(Debug.MAP_OPENER_DEBUG, Errors.READ_MAP_NAME_ERROR)
            error(Errors.READ_MAP_NAME_ERROR)
        }
        if (objectJsonName.isNullOrEmpty()) {
            Log.e(Debug.MAP_OPENER_DEBUG, Errors.READ_OBJECTS_JSON_NAME_ERROR)
            error(Errors.READ_OBJECTS_JSON_NAME_ERROR)
        }
        if (dataTypesMap.isNullOrEmpty()) {
            Log.e(Debug.MAP_OPENER_DEBUG, Errors.READ_DATA_TYPES_MAP_ERROR)
            error(Errors.READ_DATA_TYPES_MAP_ERROR)
        }
    }
    private fun launchIntent(
        activityLauncher: ActivityResultLauncher<Intent>,
        context: Context,
        mapName: String,
        objectJsonName: String,
        dataTypesMap: MutableMap<String, DataType>
    ) {
        Intent(context, MapActivity::class.java).apply {
            putExtra(Keys.MAP_NAME_KEY, mapName)
            putExtra(Keys.MAP_OBJECT_JSON_KEY, objectJsonName)
            putExtra(Keys.DATA_TYPES_MAP_KEY, HashMap(dataTypesMap))
            activityLauncher.launch(this)
        }
    }
}