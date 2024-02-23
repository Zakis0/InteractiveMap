package com.example.interactivemap

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader

object JSONParser {
    private const val OBJECT_DATA_TYPE_KEY = "DataType"
    private const val OBJECT_NAME_KEY = "Name"
    private const val OBJECT_COLOR_KEY = "Color"
    private const val OBJECT_ICON_KEY = "Icon"
    private const val OBJECT_INSIDE_OBJECT_JSON_KEY = "InsideObject"
    private const val OBJECT_PATH_KEY = "Path"

    private val DEFAULT_KEYS = setOf(
        OBJECT_DATA_TYPE_KEY,
        OBJECT_NAME_KEY,
        OBJECT_COLOR_KEY,
        OBJECT_ICON_KEY,
        OBJECT_INSIDE_OBJECT_JSON_KEY,
        OBJECT_PATH_KEY
    )

    private const val INT_DATA_TYPE = "Int"
    private const val DOUBLE_DATA_TYPE = "Double"
    private const val BOOL_DATA_TYPE = "Bool"
    private const val STRING_DATA_TYPE = "String"
    private const val BIG_STRING_DATA_TYPE = "BigString"

    private const val DATA_TYPE_JSON_PATH = "dataTypes"
    private const val MAIN_JSON_PATH = "main"

    private fun readJSONFromAssets(context: Context, path: String): String {
        try {
            val file = context.assets.open("${path}.json")
            val bufferedReader = BufferedReader(InputStreamReader(file))

            val stringBuilder = StringBuilder()
            bufferedReader.useLines { lines ->
                lines.forEach {
                    stringBuilder.append(it)
                }
            }
            return stringBuilder.toString()
        } catch (e: Exception) {
            Log.e(GLOBAL_DEBUG, "JSON reading Error: $e")
            e.printStackTrace()
            return ""
        }
    }
    private fun parseJsonToMapOfListOfMaps(jsonString: String): Map<String, List<Map<String, Any>>> {
        val gson = Gson()
        val type = object : TypeToken<Map<String, List<Map<String, Any>>>>() {}.type
        return gson.fromJson(jsonString, type)
    }
    fun getJSONsInteractiveObjectsListList(context: Context, rootPath: String): MutableList<InteractiveObject> {
        val jsonString = readJSONFromAssets(context, "$rootPath/$MAIN_JSON_PATH")
        val jsonMap = parseJsonToMapOfListOfMaps(jsonString)
        val interactiveObjectsList = mutableListOf<InteractiveObject>()

        val dataTypesMap = getDataTypesMap(context, "$rootPath/$DATA_TYPE_JSON_PATH")

        jsonMap[jsonMap.keys.first()]?.forEach { map ->
            val dataTypeName = map[OBJECT_DATA_TYPE_KEY] as String
            val name = map[OBJECT_NAME_KEY] as String
            val insideObject = map[OBJECT_INSIDE_OBJECT_JSON_KEY] as String
            val icon = map[OBJECT_ICON_KEY] as String

            val pathArray = map[OBJECT_PATH_KEY] as ArrayList<ArrayList<Float>>

            val colorArray = map[OBJECT_COLOR_KEY] as ArrayList<Int>

            val path: MutableList<Coords> = mutableListOf()
            val color = MyColor(colorArray[0], colorArray[1], colorArray[2])

            pathArray.forEach {
                path.add(Coords(it[0], it[1]))
            }
            val interactiveObject = InteractiveObject(
                dataTypeName,
                path,
                name,
                color,
                insideObject,
                icon
            )
            for (key in map.keys) {
                if (key in DEFAULT_KEYS) {
                    continue
                }
                when {
                    dataTypesMap[dataTypeName]?.ints?.contains(key)!! -> {
                        interactiveObject.ints[key] = (map[key] as Double).toInt()
                    }
                    dataTypesMap[dataTypeName]?.doubles?.contains(key)!! -> {
                        interactiveObject.doubles[key] = map[key] as Double
                    }
                    dataTypesMap[dataTypeName]?.bools?.contains(key)!! -> {
                        interactiveObject.bools[key] = map[key] as Boolean
                    }
                    dataTypesMap[dataTypeName]?.strings?.contains(key)!! -> {
                        interactiveObject.strings[key] = map[key] as String
                    }
                    dataTypesMap[dataTypeName]?.bigStrings?.contains(key)!! -> {
                        interactiveObject.bigStrings[key] = map[key] as String
                    }
                }
            }
            interactiveObjectsList.add(interactiveObject)
        }
        return interactiveObjectsList
    }
    private fun parseJsonToMapOfMaps(jsonString: String): Map<String, Map<String, Any>> {
        val gson = Gson()
        val type = object : TypeToken<Map<String, Map<String, Any>>>() {}.type
        return gson.fromJson(jsonString, type)
    }
    private fun getDataTypesMap(context: Context, rootPath: String): MutableMap<String, DataType> {
        val dataTypesMap = mutableMapOf<String, DataType>()
        val jsonString = readJSONFromAssets(context, rootPath)
        val jsonMap = parseJsonToMapOfMaps(jsonString)
        jsonMap.forEach { (dataTypeName, params) ->
            val dataType = DataType(dataTypeName)
            params.forEach { (key, value) ->
                when (value as String) {
                    INT_DATA_TYPE -> dataType.ints.add(key)
                    DOUBLE_DATA_TYPE -> dataType.doubles.add(key)
                    BOOL_DATA_TYPE -> dataType.bools.add(key)
                    STRING_DATA_TYPE -> dataType.strings.add(key)
                    BIG_STRING_DATA_TYPE -> dataType.bigStrings.add(key)
                }
            }
            dataTypesMap[dataTypeName] = dataType
        }
        return dataTypesMap
    }
}