package com.example.cityfinder.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CityService {
    val api : CityApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/dastagirkhan/00a6f6e32425e0944241/raw/33ca4e2b19695b2b93f490848314268ed5519894/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CityApiInterface::class.java)
    }
}