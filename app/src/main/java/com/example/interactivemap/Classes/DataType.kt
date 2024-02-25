package com.example.interactivemap.Classes

import java.io.Serializable

class DataType(val dataTypeName: String): Serializable {
    val ints = mutableSetOf<String>()
    val doubles = mutableSetOf<String>()
    val bools = mutableSetOf<String>()
    val strings = mutableSetOf<String>()
    val bigStrings = mutableSetOf<String>()

    override fun toString(): String {
        var resultString = dataTypeName
        if (ints.isNotEmpty()) resultString += "\nInts:"
        ints.forEach { resultString += " $it" }
        if (doubles.isNotEmpty()) resultString += "\nDoubles:"
        doubles.forEach { resultString += " $it" }
        if (bools.isNotEmpty()) resultString += "\nBools:"
        bools.forEach { resultString += " $it" }
        if (strings.isNotEmpty()) resultString += "\nStrings:"
        strings.forEach { resultString += " $it" }
        if (bigStrings.isNotEmpty()) resultString += "\nBigStrings:"
        bigStrings.forEach { resultString += " $it" }
        return "$resultString\n"
    }

    companion object {
        const val NULL_DATA_TYPE_NAME = "NULL"

        const val INT_DATA_TYPE = "Int"
        const val DOUBLE_DATA_TYPE = "Double"
        const val BOOL_DATA_TYPE = "Bool"
        const val STRING_DATA_TYPE = "String"
        const val BIG_STRING_DATA_TYPE = "BigString"
    }
}