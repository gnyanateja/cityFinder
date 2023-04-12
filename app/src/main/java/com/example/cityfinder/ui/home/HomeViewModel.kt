package com.example.cityfinder.ui.home

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cityfinder.model.CityModel
import com.example.cityfinder.utils.CityService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList


class HomeViewModel: ViewModel() {

    private val savedCities = MutableLiveData<ArrayList<CityModel>>()
    private var allCities = MutableLiveData<ArrayList<CityModel>>()
    private val gson = Gson()

    init {
        //fetch valuesx
        fetchAllCitiesList()
    }

    fun updateCities(cityModel: CityModel){
        savedCities.value?.add(cityModel)
    }

    fun observeSavedCitiesLiveData(): LiveData<ArrayList<CityModel>> {
        return savedCities
    }

    fun observeAllCitiesLiveData(): LiveData<ArrayList<CityModel>> {
        return allCities
    }

    fun fetchSavedCities(context: Context){
        try{
            val content = StringBuilder()
            try {
                val br = BufferedReader(FileReader(getFile(context)))
                var line: String?
                while (br.readLine().also { line = it } != null) {
                    content.append(line)
                }
                br.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if(context.toString().isEmpty()){
                Log.d("TAG", "fetchCity: no data")
                savedCities.value?.clear()
                savedCities.value = ArrayList()
            } else{
                val typeToken: Type = object : TypeToken<ArrayList<CityModel>>() {}.type
//            val reader = JsonReader(StringReader(content))
//            reader.isLenient = true
                Log.d("TAG", "fetchCity: "+content)
                val jsonObjs = gson.fromJson(content.toString(), typeToken) as ArrayList<CityModel>
                savedCities.value = ArrayList()
                savedCities.value?.addAll(jsonObjs)
            }
        } catch (ex:java.lang.Exception){
            savedCities.value = ArrayList()
        }
    }

    fun saveCity(context: Context, cityModel: CityModel){
        val fos: FileOutputStream = context.openFileOutput(getFile(context).name, MODE_PRIVATE)
        if(savedCities.value!=null && savedCities.value!!.contains(cityModel)){
            Toast.makeText(context,"City was already saved!", Toast.LENGTH_SHORT).show()
            return
        }
        savedCities.value?.add(cityModel)
        val typeToken: Type = object : TypeToken<ArrayList<CityModel>>() {}.type
        val content = gson.toJson(savedCities.value, typeToken)
        Log.d("TAG", "saveCity: "+content)
        fos.write(content.toByteArray())
        fos.close()
    }

    private fun getFile(context: Context): File {
        val dirPath: String = context.filesDir.absolutePath + File.separator.toString() + "SavedCities.json"
        val file = File(dirPath)
        if(!file.exists()){
            file.createNewFile()
        }
        return file
    }

    fun fetchAllCitiesList(){
        CityService.api.getPopularMovies().enqueue(object : Callback<ArrayList<CityModel>> {
            override fun onResponse(
                call: Call<ArrayList<CityModel>>,
                response: Response<ArrayList<CityModel>>
            ) {
               allCities.value = response.body()!!
            }

            override fun onFailure(call: Call<ArrayList<CityModel>>, t: Throwable) {

            }
        })
    }
}