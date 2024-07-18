package com.example.semestral.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.semestral.R
import com.example.semestral.models.Comida

class RecetaRecyclerViewAdapter(
    private val recetas: List<Comida>,
    private val onItemClick: (Comida) -> Unit
) : RecyclerView.Adapter<RecetaRecyclerViewAdapter.RecetaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_receta_card, parent, false)
        return RecetaViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecetaViewHolder, position: Int) {
        val receta = recetas[position]
        holder.bind(receta, onItemClick)
    }

    override fun getItemCount(): Int = recetas.size

    class RecetaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.receta_image)
        private val textView: TextView = itemView.findViewById(R.id.receta_name)

        fun bind(receta: Comida, onItemClick: (Comida) -> Unit) {
            textView.text = receta.strMeal
            Glide.with(itemView.context)
                .load(receta.strMealThumb)
                .into(imageView)

            itemView.setOnClickListener { onItemClick(receta) }
        }
    }
}
