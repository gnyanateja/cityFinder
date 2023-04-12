package com.example.cityfinder.ui.city


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.cityfinder.R
import com.example.cityfinder.databinding.FragmentCityDetailBinding
import com.example.cityfinder.model.CityModel
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class CityDetailFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentCityDetailBinding? = null

    private val binding get() = _binding!!
    private lateinit var map: GoogleMap
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var cityDetails:CityModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCityDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment)!!
        mapFragment.getMapAsync(this)
        binding.toolbar.visibility = View.GONE
        binding.tbHeader.text = cityDetails.name
        binding.toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(),
            R.drawable.ic_back
        )
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(gMap: GoogleMap) {
        binding.pbView.visibility = View.GONE
        binding.toolbar.visibility = View.VISIBLE
        map = gMap
        map.isTrafficEnabled = true;
        map.isIndoorEnabled = true;
        map.isBuildingsEnabled = true;
        map.uiSettings.isZoomControlsEnabled = true;
        val placeLocation = LatLng(cityDetails.lat, cityDetails.lon)
        map.addMarker(MarkerOptions().position(placeLocation).title(cityDetails.name))
        map.moveCamera(CameraUpdateFactory.newLatLng(placeLocation));
        map.animateCamera(CameraUpdateFactory.zoomTo(10F), 1000, null);
    }

    companion object {
        fun createFragment(cityModel: CityModel): CityDetailFragment {
            val cityDetailFragment = CityDetailFragment()
            cityDetailFragment.cityDetails = cityModel
            return cityDetailFragment
        }
    }
}