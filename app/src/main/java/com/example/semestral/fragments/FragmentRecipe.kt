package com.example.semestral.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.semestral.R
import com.example.semestral.adapters.RecyclerViewRecipe
import com.example.semestral.conexion.RetrofitClient
import com.example.semestral.models.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentRecipe : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewRecipe: RecyclerViewRecipe
    private lateinit var textViewCategorias: TextView
    private lateinit var backButton: ImageButton
    private var recipes: List<Recipe> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        textViewCategorias = view.findViewById(R.id.receta)
        backButton = view.findViewById(R.id.back_button)

        recyclerView.layoutManager = LinearLayoutManager(context)

        backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        // Aquí puedes llamar a obtenerRecetasPorCategoria con la categoría deseada
        obtenerRecetasPorCategoria("Seafood")

        return view
    }

    private fun obtenerRecetasPorCategoria(categoria: String) {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.api.obtenerRecetas(categoria)
                }
                response.meals?.let { recetas ->
                    recipes = recetas
                    recyclerViewRecipe = RecyclerViewRecipe(recipes)
                    recyclerView.adapter = recyclerViewRecipe
                } ?: Log.d("FragmentRecipe", "La lista de recetas está vacía o nula.")
            } catch (e: Exception) {
                Log.e("FragmentRecipe", "Error en la llamada: ${e.message}")
            }
        }
    }
}
