package com.example.semestral.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.semestral.R
<<<<<<< Updated upstream
=======
import com.example.semestral.interfaces.ComidaAPI
import com.example.semestral.conexion.RetrofitClient
import com.example.semestral.fragments.FragmentCategoriasList
>>>>>>> Stashed changes
import com.example.semestral.fragments.FragmentHome
import com.example.semestral.fragments.ProfileFragment
import com.example.semestral.fragments.RecetaVista
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
                    replaceFragment(FragmentCategoriasList())
                    true
                }
                R.id.nav_search -> {
<<<<<<< Updated upstream
                    replaceFragment(RecetaVista())
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(ProfileFragment())
=======
                    replaceFragment(FragmentCategoriasList())
                    true
                }
                R.id.nav_profile -> {
                    replaceFragment(FragmentCategoriasList())
>>>>>>> Stashed changes
                    true
                }
                else -> false
            }
        }

<<<<<<< Updated upstream
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
=======
        // Set default fragment on start
        replaceFragment(FragmentCategoriasList())
>>>>>>> Stashed changes
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
