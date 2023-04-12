package com.example.cityfinder.ui.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cityfinder.databinding.ItemCityBinding
import com.example.cityfinder.listeners.SelectListener
import com.example.cityfinder.model.CityModel

class CityListAdapter(var cityList: ArrayList<CityModel>, val listener: SelectListener): RecyclerView.Adapter<CityListAdapter.CityViewHolder>() {

    class CityViewHolder(val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root)

    fun addCityList(newCityList: ArrayList<CityModel>){
        cityList.clear()
        cityList.addAll(newCityList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
            return CityViewHolder(
                ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.binding.city = cityList[position]
        holder.itemView.setOnClickListener {
            listener.OnCityClicked(cityList[position])
        }
    }

    override fun onBindViewHolder(
        holder: CityViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        onBindViewHolder(holder, position)
    }


    override fun getItemCount(): Int {
        return cityList.size
    }
}