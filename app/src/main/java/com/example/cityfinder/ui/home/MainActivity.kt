package com.example.cityfinder.ui.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.example.cityfinder.ui.city.CityFragment
import com.example.cityfinder.R
import com.example.cityfinder.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelProvider = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModelProvider.fetchSavedCities(this)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.homeFragment, HomeFragment())
            .addToBackStack(null)
            .commit()

        hideFab()
        binding.fab.setOnClickListener { view ->
            if(!isOnline(view.context)) {
                Snackbar.make(view, "Check your internet connection!", Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.fab)
                    .setAction("Action", null).show()
            } else {
                hideFab()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.homeFragment, CityFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    fun showFab(){
        binding.fab.visibility = View.VISIBLE
    }

    fun hideFab(){
        binding.fab.visibility = View.GONE
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count < 2) {
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

}