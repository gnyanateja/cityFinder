package com.example.cityfinder.utils

import com.example.cityfinder.model.CityModel
import retrofit2.Call
import retrofit2.http.GET

interface CityApiInterface {

    @GET("gistfile1.json")
    fun getPopularMovies() : Call<ArrayList<CityModel>>
}