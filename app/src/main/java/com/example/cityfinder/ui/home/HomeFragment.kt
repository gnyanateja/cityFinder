package com.example.cityfinder.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cityfinder.R
import com.example.cityfinder.databinding.FragmentHomeBinding
import com.example.cityfinder.listeners.SelectListener
import com.example.cityfinder.model.CityModel
import com.example.cityfinder.ui.city.CityDetailFragment
import com.example.cityfinder.ui.city.CityListAdapter


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private val sharedViewModel: HomeViewModel by activityViewModels()
    private lateinit var cityAdapter : CityListAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).showFab()
        binding.toolbar.visibility = View.GONE
        sharedViewModel.observeSavedCitiesLiveData().observe(viewLifecycleOwner, Observer{
            binding.pbView.visibility = View.GONE
            binding.toolbar.visibility = View.VISIBLE
            cityAdapter = CityListAdapter(it as ArrayList<CityModel>, object : SelectListener {
                override fun OnCityClicked(cityModel: CityModel) {
                    parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.homeFragment, CityDetailFragment.createFragment(cityModel))
                        .addToBackStack(null)
                        .commit()
                }
            })
            binding.rvCities.apply {
                adapter = cityAdapter
                layoutManager = LinearLayoutManager(context)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).hideFab()
        _binding = null
    }
}