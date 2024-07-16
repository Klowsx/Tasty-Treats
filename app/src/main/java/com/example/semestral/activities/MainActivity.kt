//Madelaine Arosemena, ALvaro Frago, Osiris Mateo, Javier Hernandez
package com.example.semestral.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.semestral.R
import com.example.semestral.interfaces.ComidaAPI
import com.example.semestral.conexion.RetrofitClient
import com.example.semestral.fragments.FragmentHome
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(){

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    replaceFragment(FragmentHome())
                    true
                }
                R.id.nav_search -> {
                    replaceFragment(FragmentHome())
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(FragmentHome())
                    true
                }
                else -> false
            }
        }

        // Set default fragment on start
        replaceFragment(FragmentHome())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}