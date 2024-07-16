package com.example.semestral.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.semestral.R
import com.example.semestral.conexion.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentHome : Fragment() {

    private lateinit var viewFlipper: ViewFlipper
    private lateinit var imgRecetaRandom: ImageView
    private lateinit var tituloRecetaRandom: TextView
    private lateinit var nombreRecetaRandom: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize views
        viewFlipper = view.findViewById(R.id.viewFlipper)
        imgRecetaRandom = view.findViewById(R.id.imgRecetaRandom)
        tituloRecetaRandom = view.findViewById(R.id.tituloRecetaRandom)
        nombreRecetaRandom = view.findViewById(R.id.nombreRecetaRandom)

        viewFlipper.inAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.in_from_right)
        viewFlipper.outAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.out_to_left)

        obtenerRecetaRandom()
        // Return the inflated view
        return view
    }

    private fun obtenerRecetaRandom() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.api.obtenerRandom()
                withContext(Dispatchers.Main) {
                    if (response.comidas.isNullOrEmpty()) {
                        Log.d("FragmentHome", "La lista de comidas es nula o vacía.")
                        // Manejar el caso cuando no hay comidas disponibles
                    } else {
                        val comida = response.comidas.firstOrNull()
                        comida?.let {
                            Log.d("FragmentHome", "Receta aleatoria obtenida: ${it.strMeal}")
                            Log.d("FragmentHome", "ID: ${it.idMeal}")
                            Log.d("FragmentHome", "Imagen: ${it.strMealThumb}")
                            tituloRecetaRandom.text = "Receta aleatoria"
                            nombreRecetaRandom.text = it.strMeal
                            Glide.with(requireContext()).load(it.strMealThumb).into(imgRecetaRandom)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("FragmentHome", "Error al obtener receta aleatoria: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    // Manejar el error de manera apropiada, por ejemplo, mostrar un mensaje al usuario
                }
            }
        }
    }


}
