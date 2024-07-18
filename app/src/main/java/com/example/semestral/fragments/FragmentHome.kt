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
import androidx.fragment.app.commit
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
    private lateinit var idMeal: String

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

        // Configure animations for ViewFlipper
        viewFlipper.inAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.in_from_right)
        viewFlipper.outAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.out_to_left)

        // Load random recipe and specific categories
        obtenerRecetaRandom()
        obtenerCategoriasEspecificas()

        // Click listener for random recipe image
        imgRecetaRandom.setOnClickListener {
            Log.d("FragmentHome", "Imagen de receta random clickeada")
            Log.d("AAAA", "ID Guardado: $idMeal")
            idMeal?.let { idMeal ->
                val args = Bundle().apply {
                    putString("idMeal", idMeal)
                }
                val recetaVistaFragment = RecetaVista().apply {
                    arguments = args
                }
                parentFragmentManager.commit {
                    replace(R.id.fragment_container, recetaVistaFragment)
                    addToBackStack(null)
                    Log.d("FragmentHome", "Reemplazando fragmento por com.example.semestral.fragments.RecetaVista")
                }
            }
        }

        // Handle flipper navigation
        view.findViewById<View>(R.id.btnNext)?.setOnClickListener {
            viewFlipper.inAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.in_from_right)
            viewFlipper.outAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.out_to_left)
            viewFlipper.showNext()
        }

        view.findViewById<View>(R.id.btnPrevious)?.setOnClickListener {
            viewFlipper.inAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.in_from_left)
            viewFlipper.outAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.out_to_right)
            viewFlipper.showPrevious()
        }

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
                    idMeal = comida.idMeal

                    tituloRecetaRandom.text = "Receta aleatoria"
                    nombreRecetaRandom.text = comida.strMeal
                    Glide.with(requireContext()).load(comida.strMealThumb).into(imgRecetaRandom)
                } ?: Log.d("FragmentHome", "La lista de meals está vacía o nula.")
            } catch (e: Exception) {
                Log.e("FragmentHome", "Error en la llamada: ${e.message}")
            }
        }
    }

    private fun obtenerCategoriasEspecificas() {
        val categoriasFiltradasIds = listOf("1", "2", "7", "8")

        lifecycleScope.launch {
            try {
                val categoriaResponse = withContext(Dispatchers.IO) {
                    RetrofitClient.api.obtenerCategorias()
                }
                val categoriasFiltradas = categoriaResponse.categories.filter { it.idCategory in categoriasFiltradasIds }

                for (categoria in categoriasFiltradas) {
                    val cardView = layoutInflater.inflate(R.layout.ingredient_item, viewFlipper, false)

                    val imgIngrediente: ImageView = cardView.findViewById(R.id.imgIngrediente)
                    val titleIngrediente: TextView = cardView.findViewById(R.id.titleIngrediente)

                    titleIngrediente.text = categoria.strCategory
                    Glide.with(requireContext()).load(categoria.strCategoryThumb).into(imgIngrediente)

                    cardView.setOnClickListener {
                        Log.d("FragmentHome", "Card de categoría clickeada: ${categoria.strCategory}")
                        navigateToRecetasFiltradas(categoria.strCategory)
                    }

                    viewFlipper.addView(cardView)
                }

                // Start flipping views in ViewFlipper
                viewFlipper.isAutoStart = true
                viewFlipper.flipInterval = 5000
                viewFlipper.startFlipping()

            } catch (e: Exception) {
                Log.e("FragmentHome", "Error al obtener categorías: ${e.message}")
            }
        }
    }

    private fun navigateToRecetasFiltradas(categoria: String) {
        val fragmentRecetasFiltradas = FragmentRecetasFiltradas().apply {
            arguments = Bundle().apply {
                putString("categoria", categoria)
            }
        }
        parentFragmentManager.commit {
            replace(R.id.fragment_container, fragmentRecetasFiltradas)
            addToBackStack(null)
        }
    }
}
