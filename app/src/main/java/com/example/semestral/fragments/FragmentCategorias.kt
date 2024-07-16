package com.example.semestral.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.semestral.R
import com.example.semestral.models.Categoria

class FragmentCategorias : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categorias_list, container, false)

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Dummy data
        val categorias = listOf(
            Categoria("1", "Beef", "", "Description of Beef"),
            Categoria("2", "Chicken", "", "Description of Chicken")
            // Add more categories as needed
        )

        // Set adapter
        recyclerView.adapter = RecyclerViewAdapter(categorias)

        return view
    }
}