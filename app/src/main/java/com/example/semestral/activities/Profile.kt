package com.example.semestral.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.semestral.R
import com.example.semestral.R.id.bottom_navigation2
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Profile : AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val textView = findViewById<TextView>(R.id.name)

        val auth = Firebase.auth
        val user = auth.currentUser

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        
        val bottomNavigationView = findViewById<BottomNavigationView>(bottom_navigation2)
        val colorStateList = resources.getColorStateList(R.color.nav_option_selected)

        bottomNavigationView.itemIconTintList = colorStateList
       bottomNavigationView.itemTextColor = colorStateList

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Acción para el ítem de Home
                    startActivity(Intent(this, MainActivity::class.java))
                    item.isChecked = true
                    true
                }
                R.id.nav_search -> {
                    // Acción para el ítem de Search
                    startActivity(Intent(this, MainActivity::class.java))
                    item.isChecked = true
                    true
                }
                R.id.nav_profile -> {
                    // Acción para el ítem de Profile
                    startActivity(Intent(this, Profile::class.java))
                    item.isChecked = true
                    true
                }
                else -> false

            }
        }
        if (user != null) {
            val userName = user.displayName
            textView.text = "Welcome, $userName"
        }

        val signOutButton = findViewById<Button>(R.id.logout_button)
        signOutButton.setOnClickListener {
            signOutAndStartSignInActivity()
        }
    }

    private fun signOutAndStartSignInActivity() {
        mAuth.signOut()

        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
            val intent = Intent(this@Profile, SignIn::class.java)
            startActivity(intent)
            finish()
        }
    }
}
