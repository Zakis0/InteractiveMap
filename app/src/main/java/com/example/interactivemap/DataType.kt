package com.example.interactivemap

class DataType(val dataTypeName: String) {
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
}