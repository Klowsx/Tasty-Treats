//Madelaine Arosemena, ALvaro Frago, Osiris Mateo, Javier Hernandez
package com.example.semestral.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import com.example.semestral.R
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {


    @SuppressLint("UseCompatLoadingForColorStateLists")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewFlipper: ViewFlipper = findViewById(R.id.viewFlipper)
        viewFlipper.inAnimation = AnimationUtils.loadAnimation(this, R.anim.in_from_right)
        viewFlipper.outAnimation = AnimationUtils.loadAnimation(this, R.anim.out_to_left)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val colorStateList = resources.getColorStateList(R.color.nav_option_selected)

        bottomNavigationView.itemIconTintList = colorStateList
        bottomNavigationView.itemTextColor = colorStateList

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Acción para el ítem de Home
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_search -> {
                    // Acción para el ítem de Search
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    // Acción para el ítem de Profile
                    startActivity(Intent(this, Profile::class.java))
                    true
                }
                else -> false
            }
    }


}
}

