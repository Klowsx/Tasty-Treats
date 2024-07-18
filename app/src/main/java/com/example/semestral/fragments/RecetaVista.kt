package com.example.semestral.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.semestral.R
import com.example.semestral.conexion.RetrofitClient
import com.example.semestral.models.Comida
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecetaVista : Fragment() {
    lateinit var imgDetalles: ImageView
    lateinit var titleComida: TextView
    lateinit var titleCategoria: TextView
    lateinit var titleOrigen: TextView
    lateinit var listaIngredientes: TextView
    lateinit var listaPasos: TextView
    lateinit var savebutton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_receta_vista, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgDetalles = view.findViewById(R.id.imgDetalles)
        titleComida = view.findViewById(R.id.titleComida)
        titleCategoria = view.findViewById(R.id.titleCategoria)
        titleOrigen = view.findViewById(R.id.titleOrigen)
        listaIngredientes = view.findViewById(R.id.listaIngredientes)
        listaPasos = view.findViewById(R.id.listaPasos)
        savebutton= view.findViewById(R.id.imageButtonSave)
        // Handle back button click
        view.findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            activity?.onBackPressed()
        }

        // Obtener el ID de la comida del Bundle
        val idMeal = arguments?.getString("idMeal")

        // Obtener los detalles de la comida usando el ID
        idMeal?.let {
            obtenerDetallesComida(it)
        }
        savebutton.setOnClickListener {
            idMeal?.let {
                toggleSaveRecipe(it)
                checkIfRecipeIsSaved(it)
            }
        }
    }

    private fun obtenerDetallesComida(idMeal: String) {
        lifecycleScope.launch {
            try {
                val comidaResponse = withContext(Dispatchers.IO) {
                    RetrofitClient.api.obtenerComidaPorId(idMeal)
                }
                comidaResponse.meals?.firstOrNull()?.let { comida ->
                    Log.d("RecetaVista", "Detalles de la comida: ${comida.strMeal}")

                    // Mostrar detalles en la UI
                    Glide.with(requireContext()).load(comida.strMealThumb).into(imgDetalles)
                    titleComida.text = comida.strMeal
                    titleCategoria.text = comida.strCategory
                    titleOrigen.text = comida.strArea

                    // Filtrar ingredientes y medidas no vacíos
                    val ingredientes = getIngredientesNoVacios(comida)
                    listaIngredientes.text = ingredientes.joinToString("\n")

                    // Mostrar instrucciones de preparación
                    listaPasos.text = comida.strInstructions

                    // Verificar si la receta está guardada
                    checkIfRecipeIsSaved(comida.idMeal)

                } ?: Log.d("RecetaVista", "La comida no se encontró.")
            } catch (e: Exception) {
                Log.e("RecetaVista", "Error en la llamada: ${e.message}")
            }
        }
    }

    ///////////////
private fun toggleSaveRecipe(idMeal: String) {
    val sharedPreferences = requireActivity().getSharedPreferences("com.example.semestral.activities.RecetasGuardadas", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val savedRecipes = sharedPreferences.getStringSet("recetas", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

    if (savedRecipes.contains(idMeal)) {
        savedRecipes.remove(idMeal)
        savebutton.setImageResource(R.drawable.ic_save_outline)
    } else {
        savedRecipes.add(idMeal)
        savebutton.setImageResource(R.drawable.ic_save_fill)
    }

    editor.putStringSet("recetas", savedRecipes)
    editor.apply()

    // Volver al fragmento de recetas guardadas después de guardar/eliminar
    requireActivity().supportFragmentManager.popBackStack()
}


    private fun checkIfRecipeIsSaved(idMeal: String) {
        val sharedPreferences = requireActivity().getSharedPreferences("com.example.semestral.activities.RecetasGuardadas", Context.MODE_PRIVATE)
        val savedRecipes = sharedPreferences.getStringSet("recetas", mutableSetOf()) ?: mutableSetOf()

        if (savedRecipes.contains(idMeal)) {
            savebutton.setImageResource(R.drawable.ic_save_fill)
        } else {
            savebutton.setImageResource(R.drawable.ic_save_outline)
        }
    }
    // Función para obtener ingredientes no vacíos
    private fun getIngredientesNoVacios(comida: Comida): List<String> {
        val ingredientes = mutableListOf<String>()

        // Iterar sobre las propiedades de ingredientes y medidas
        for (i in 1..20) {
            val ingrediente = comida.getIngrediente(i)
            val medida = comida.getMedida(i)

            if (ingrediente.isNotBlank() && medida.isNotBlank()) {
                ingredientes.add("$medida - $ingrediente")
            }
        }

        return ingredientes
    }

    // Función de extensión para obtener ingredientes por índice
    private fun Comida.getIngrediente(index: Int): String {
        return when (index) {
            1 -> strIngredient1
            2 -> strIngredient2
            3 -> strIngredient3
            4 -> strIngredient4
            5 -> strIngredient5
            6 -> strIngredient6
            7 -> strIngredient7
            8 -> strIngredient8
            9 -> strIngredient9
            10 -> strIngredient10
            11 -> strIngredient11
            12 -> strIngredient12
            13 -> strIngredient13
            14 -> strIngredient14
            15 -> strIngredient15
            16 -> strIngredient16
            17 -> strIngredient17
            18 -> strIngredient18
            19 -> strIngredient19
            20 -> strIngredient20
            else -> ""
        }
    }

    // Función de extensión para obtener medidas por índice
    private fun Comida.getMedida(index: Int): String {
        return when (index) {
            1 -> strMeasure1
            2 -> strMeasure2
            3 -> strMeasure3
            4 -> strMeasure4
            5 -> strMeasure5
            6 -> strMeasure6
            7 -> strMeasure7
            8 -> strMeasure8
            9 -> strMeasure9
            10 -> strMeasure10
            11 -> strMeasure11
            12 -> strMeasure12
            13 -> strMeasure13
            14 -> strMeasure14
            15 -> strMeasure15
            16 -> strMeasure16
            17 -> strMeasure17
            18 -> strMeasure18
            19 -> strMeasure19
            20 -> strMeasure20
            else -> ""
        }
    }
}