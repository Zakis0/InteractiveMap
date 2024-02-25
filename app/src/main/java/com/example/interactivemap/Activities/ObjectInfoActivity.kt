package com.example.interactivemap.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import com.example.interactivemap.Classes.DataType
import com.example.interactivemap.Classes.InteractiveObject
import com.example.interactivemap.Constants.Debug
import com.example.interactivemap.Constants.Keys
import com.example.interactivemap.Modules.ObjectInfoReader
import com.example.interactivemap.databinding.ActivityObjectInfoBinding

class ObjectInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityObjectInfoBinding
    var interactiveObject: InteractiveObject? = null
    var dataTypesMap: MutableMap<String, DataType>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityObjectInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        interactiveObject = intent.getSerializableExtra(Keys.INTERACTIVE_OBJECT_INFO_KEY)
                as? InteractiveObject
        dataTypesMap = intent.getSerializableExtra(Keys.DATA_TYPES_MAP_KEY) as? MutableMap<String, DataType>

        Log.d(Debug.OBJECT_INFO_READER_DEBUG, interactiveObject.toString())

        ObjectInfoReader.createViewsByObjectsInfo(this)
    }
}