package com.example.cityfinder.ui.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cityfinder.R
import com.example.cityfinder.databinding.FragmentCityBinding
import com.example.cityfinder.listeners.SelectListener
import com.example.cityfinder.model.CityModel
import com.example.cityfinder.ui.home.HomeViewModel


class CityFragment : Fragment() {

    private var _binding: FragmentCityBinding? = null
    private val sharedViewModel: HomeViewModel by activityViewModels()
    private lateinit var cityAdapter : CityListAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.visibility = View.GONE
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(),
            R.drawable.ic_back
        )
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
        sharedViewModel.observeAllCitiesLiveData().observe(viewLifecycleOwner, Observer{
            binding.pbView.visibility = View.GONE
            binding.toolbar.visibility = View.VISIBLE
            cityAdapter = CityListAdapter(it, object : SelectListener{
                override fun OnCityClicked(cityModel: CityModel) {
                    sharedViewModel.saveCity(requireContext(), cityModel)
                    activity?.onBackPressed()
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
        _binding = null
    }
}