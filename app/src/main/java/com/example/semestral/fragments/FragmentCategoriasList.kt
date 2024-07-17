package com.example.semestral.fragments

import android.os.Bundle
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
import com.example.semestral.conexion.RetrofitClient
import com.example.semestral.models.Categoria
import kotlinx.coroutines.launch

class FragmentCategoriasList : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var searchView: SearchView
    private lateinit var backButton: ImageButton
    private lateinit var textViewCategorias: TextView
    private var categorias: List<Categoria> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categorias_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.search)
        backButton = view.findViewById(R.id.back_button)
        textViewCategorias = view.findViewById(R.id.categorias)

        recyclerView.layoutManager = LinearLayoutManager(context)

        backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        fetchCategorias()

        return view
    }

    private fun fetchCategorias() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.obtenerCategorias()
                categorias = response.categories
                recyclerViewAdapter = RecyclerViewAdapter(categorias)
                recyclerView.adapter = recyclerViewAdapter
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}