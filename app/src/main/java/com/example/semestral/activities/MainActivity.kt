/*Madelaine Arosemena 8-1004-178
* Javier Hernandez 8-1001-178
* Alvaro Frago 8-993-484
* Osiris Mateo 20-70-7416*/
package com.example.semestral.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.semestral.R
import com.example.semestral.fragments.FragmentCategoriasList
import com.example.semestral.fragments.FragmentHome
import com.example.semestral.fragments.ProfileFragment

import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

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
                    replaceFragment(FragmentCategoriasList())
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(ProfileFragment())

                    true
                }

                else -> false
            }
        }

        // Manejar el caso cuando se vuelve desde NuevoNombreActivity con un nuevo nombre
        val newName = intent.getStringExtra("newName")
        val navigateToProfile = intent.getBooleanExtra("navigateToProfile", false)

        if (navigateToProfile && newName != null) {
            val profileFragment = ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString("newName", newName)
                }
            }
            replaceFragment(profileFragment)
            bottomNavigationView.selectedItemId = R.id.nav_profile // Seleccionar el ítem de perfil
        } else {
            // Set default fragment on start
            replaceFragment(FragmentHome())
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
