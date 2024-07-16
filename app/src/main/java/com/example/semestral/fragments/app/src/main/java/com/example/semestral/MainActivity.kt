package com.example.semestral

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.semestral.fragments.FragmentHome
import com.example.semestral.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val colorStateList = resources.getColorStateList(R.color.nav_option_selected)
        bottomNavigationView.itemIconTintList = colorStateList
        bottomNavigationView.itemTextColor = colorStateList

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, FragmentHome())
            addToBackStack(null)
            commit()

        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Acción para el ítem de Home
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragment_container, FragmentHome())
                        addToBackStack(null)
                        commit()

                    }
                    true
                }
                R.id.nav_search -> {
                    // Acción para el ítem de Search
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    // Acción para el ítem de Profile
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragment_container, ProfileFragment())
                        addToBackStack(null)
                        commit()

                    }
                    true
                }
                else -> false
            }
        }
    }


}
