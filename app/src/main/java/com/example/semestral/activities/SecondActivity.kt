package com.example.semestral.activities




import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.semestral.R
import com.example.semestral.fragments.RecetaVista


class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, RecetaVista())
            .commit()
    }
}