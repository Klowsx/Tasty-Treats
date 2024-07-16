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
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.semestral.R
import com.example.semestral.conexion.RetrofitClient
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
        lifecycleScope.launch {
            try {
                val comidaResponse = withContext(Dispatchers.IO) {
                    RetrofitClient.api.obtenerRandom()
                }
                comidaResponse.meals?.firstOrNull()?.let { comida ->
                    Log.d("FragmentHome", "Receta aleatoria: ${comida.strMeal}")
                    Log.d("FragmentHome", "ID: ${comida.idMeal}")
                    Log.d("FragmentHome", "Imagen: ${comida.strMealThumb}")
                    tituloRecetaRandom.text = "Receta aleatoria"
                    nombreRecetaRandom.text = comida.strMeal
                    Glide.with(requireContext()).load(comida.strMealThumb).into(imgRecetaRandom)
                } ?: Log.d("FragmentHome", "La lista de meals está vacía o nula.")
            } catch (e: Exception) {
                Log.e("FragmentHome", "Error en la llamada: ${e.message}")
            }
        }
    }
}
