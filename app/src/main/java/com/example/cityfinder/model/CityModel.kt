package com.example.cityfinder.model

import android.widget.TextView
import androidx.databinding.BindingAdapter

data class CityModel(
    val name: String,
    val state: String,
    val lat: Double,
    val lon: Double
){
    companion object{

        @JvmStatic
        @BindingAdapter(value = ["preText", "dataString"])
        fun addText(view: TextView, preText:String, dataString:Any){
            view.text = preText+dataString.toString()
        }
    }
}
