package com.example.cityfinder.listeners

import com.example.cityfinder.model.CityModel

interface SelectListener {
    fun OnCityClicked(cityModel: CityModel)
}