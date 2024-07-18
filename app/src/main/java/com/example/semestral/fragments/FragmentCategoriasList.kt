package com.example.semestral.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.semestral.R
import com.example.semestral.conexion.RetrofitClient
import com.example.semestral.models.Categoria
import com.example.semestral.models.Comida
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FragmentCategoriasList : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var backButton: ImageButton
    private lateinit var textViewCategorias: TextView
    private var categorias: List<Categoria> = listOf()
    private var comidas: List<Comida> = listOf()
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categorias_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.search)
        backButton = view.findViewById(R.id.back_button)
        textViewCategorias = view.findViewById(R.id.receta)

        recyclerView.layoutManager = LinearLayoutManager(context)

        backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        fetchCategorias()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isNotEmpty()) {
                        performSearch(it)
                    } else {
                        showCategories()
                    }
                } ?: showCategories()

                return true
            }
        })

        return view
    }

    private fun performSearch(query: String) {
        searchJob?.cancel()

        searchJob = lifecycleScope.launch {
            delay(500)
            try {
                val response = RetrofitClient.api.searchRecipes(query)
                comidas = response.meals ?: listOf()
                updateRecyclerView(comidas)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun showCategories() {
        updateRecyclerView(categorias)
    }

    private fun updateRecyclerView(items: List<Any>) {
        if (items.isNotEmpty() && items.first() is Categoria) {
            recyclerView.adapter = CategoriaRecyclerViewAdapter(items as List<Categoria>) { categoria ->
                navigateToRecetasPorCategoria(categoria.strCategory)
            }
            textViewCategorias.visibility = View.VISIBLE
            recyclerView.visibility = View.VISIBLE
        } else if (items.isNotEmpty() && items.first() is Comida) {
            recyclerView.adapter = ComidaRecyclerViewAdapter(items as List<Comida>) { comida ->
                navigateToRecetaVista(comida.idMeal)
            }
            textViewCategorias.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        } else {
            textViewCategorias.visibility = View.VISIBLE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun navigateToRecetaVista(idMeal: String) {
        val recetaVistaFragment = RecetaVista().apply {
            arguments = Bundle().apply {
                putString("idMeal", idMeal)
            }
        }
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragment_container, recetaVistaFragment)
            addToBackStack(null)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            commit()
        }
    }

    private fun navigateToRecetasPorCategoria(categoria: String) {
        val fragmentRecetaFiltradas = FragmentRecetasFiltradas().apply {
            arguments = Bundle().apply {
                putString("categoria", categoria)
            }
        }
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragment_container, fragmentRecetaFiltradas)
            addToBackStack(null)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            commit()
        }
    }

    private fun fetchCategorias() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.obtenerCategorias()
                categorias = response.categories
                recyclerView.adapter = CategoriaRecyclerViewAdapter(categorias) { categoria ->
                    navigateToRecetasPorCategoria(categoria.strCategory)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
