//Madelaine Arosemena, ALvaro Frago, Osiris Mateo, Javier Hernandez
package com.example.semestral.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.semestral.R
import com.example.semestral.interfaces.ComidaAPI
import com.example.semestral.conexion.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class pruebaAPI : AppCompatActivity() {

    private lateinit var comidaAPI: ComidaAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtener la instancia de Retrofit
        comidaAPI = RetrofitClient.retrofit.create(ComidaAPI::class.java)

        // Llamada para obtener categorías
        getCategorias()
    }

    private fun getCategorias() {
        lifecycleScope.launch {
            try {
                val categoriaResponse = withContext(Dispatchers.IO) {
                    comidaAPI.obtenerCategorias()
                }
                // Maneja la lista de categorías aquí
                Log.d("MainActivity", "Categorías: ${categoriaResponse.categories}")
            } catch (e: Exception) {
                Log.e("MainActivity", "Error en la llamada: ${e.message}")
            }
        }
    }
}