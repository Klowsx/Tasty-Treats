package com.example.semestral.fragments.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.semestral.R
import com.example.semestral.models.Categoria
import com.squareup.picasso.Picasso
import androidx.cardview.widget.CardView


class CategoriaRecyclerViewAdapter(
    private val categorias: List<Categoria>,
    private val onClick: (Categoria) -> Unit // Listener para manejar clics en categorías
) : RecyclerView.Adapter<CategoriaRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.cardViewCategoria)
        val imagenCategoria: ImageView = view.findViewById(R.id.imagenCategoria)
        val tituloCat: TextView = view.findViewById(R.id.tituloCat)
        val cantidad: TextView = view.findViewById(R.id.cantidad)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_categorias, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.tituloCat.text = categoria.strCategory
        holder.cantidad.text = categoria.strCategoryDescription
        Picasso.get().load(categoria.strCategoryThumb).into(holder.imagenCategoria)

        // Manejar clic en la tarjeta de categoría
        holder.cardView.setOnClickListener {
            Log.d("CategoriaAdapter", "Clic en categoría: ${categoria.strCategory}")
            onClick(categoria) // Llamar al listener con la categoría seleccionada
        }
    }

    override fun getItemCount() = categorias.size
}

