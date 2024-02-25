package com.example.interactivemap.Classes

import java.io.Serializable

enum class ClickModes(val modeName: String): Serializable {
    OPEN_INFO ("Info"),
    OPEN_INSIDE_OBJECT("Inside"),
    MOVE("Move");
}