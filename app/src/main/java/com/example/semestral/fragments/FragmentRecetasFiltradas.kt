package com.example.semestral.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.semestral.R
import com.example.semestral.conexion.RetrofitClient
import com.example.semestral.models.Comida
import kotlinx.coroutines.launch

class FragmentRecetasFiltradas : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var backButton: ImageButton
    private lateinit var tituloTextView: TextView
    private var comidas: List<Comida> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recetas_filtradas, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        backButton = view.findViewById(R.id.back_button)
        tituloTextView = view.findViewById(R.id.titulo)

        val categoria = arguments?.getString("categoria") ?: ""
        tituloTextView.text = categoria

        recyclerView.layoutManager = GridLayoutManager(context, 2)
        fetchRecetasPorCategoria(categoria)

        backButton.setOnClickListener {
            activity?.onBackPressed()
        }

        return view
    }

    private fun fetchRecetasPorCategoria(categoria: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.obtenerRecetasFiltradas(categoria)
                comidas = response.meals ?: listOf()
                recyclerView.adapter = RecetaRecyclerViewAdapter(comidas) { comida ->
                    navigateToRecetaVista(comida.idMeal)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
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
}
