package com.example.semestral.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.semestral.R

class RecetasGuardadasFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecetaAdapter
    private lateinit var  textViewNoSave: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recetas_guardadas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewRecetas)
        adapter = RecetaAdapter(requireContext())
        textViewNoSave= view.findViewById(R.id.tvNoRecetas)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Cargar recetas guardadas desde SharedPreferences y pasarlas al adaptador
        loadSavedRecipes()
    }

    private fun loadSavedRecipes() {
        val sharedPreferences = requireActivity().getSharedPreferences(
            "com.example.semestral.activities.RecetasGuardadas",
            Context.MODE_PRIVATE
        )
        val savedRecipes = sharedPreferences.getStringSet("recetas", mutableSetOf()) ?: mutableSetOf()

        adapter.submitList(savedRecipes.toList())
    }
    private fun NoSaveText(){
        if (recyclerView.adapter?.itemCount == 0) {
            // Muestra el TextView si el RecyclerView está vacío
            textViewNoSave.visibility = View.VISIBLE
        } else {
            // Oculta el TextView si el RecyclerView tiene elementos
            textViewNoSave.visibility = View.GONE
        }
    }
}
