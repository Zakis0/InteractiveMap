package com.example.interactivemap.Classes

class InteractiveObject(
    var dataType: String,
    var path: MutableList<Coords>,
    var name: String = "",
    var color: MyColor = MyColor.GRAY,
    var insideObject: String = "",
    var icon: String = ""
) {
    val ints = mutableMapOf<String, Int>()
    val doubles = mutableMapOf<String, Double>()
    val bools = mutableMapOf<String, Boolean>()
    val strings = mutableMapOf<String, String>()
    val bigStrings = mutableMapOf<String, String>()

    val canvasPath = CanvasPath(path)

    override fun toString(): String {
        var resultString = "Type Name: $dataType\n" +
                "Name: $name\n" +
                "Color: $color\n" +
                "Inside Object: $insideObject\n" +
                "Path: $path"
        ints.forEach {
            resultString += "\n${it.key}: ${it.value}"
        }
        doubles.forEach {
            resultString += "\n${it.key}: ${it.value}"
        }
        bools.forEach {
            resultString += "\n${it.key}: ${it.value}"
        }
        strings.forEach {
            resultString += "\n${it.key}: ${it.value}"
        }
        bigStrings.forEach {
            resultString += "\n${it.key}: ${it.value}"
        }
        return resultString
    }
}

