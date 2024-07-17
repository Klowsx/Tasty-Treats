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

class ComidaRecyclerViewAdapter(private val comidas: List<Comida>) :
    RecyclerView.Adapter<ComidaRecyclerViewAdapter.ComidaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComidaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comida, parent, false)
        return ComidaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComidaViewHolder, position: Int) {
        val comida = comidas[position]
        holder.bind(comida)
    }

    override fun getItemCount(): Int {
        return comidas.size
    }

    class ComidaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewNombre: TextView = itemView.findViewById(R.id.textViewNombre)
        private val textViewOrigen: TextView = itemView.findViewById(R.id.textViewOrigen)
        private val imageViewThumbnail: ImageView = itemView.findViewById(R.id.imageViewThumbnail)


        fun bind(comida: Comida) {
            itemView.apply {
                // Asignar datos a los elementos de la tarjeta
                textViewNombre.text = comida.strMeal
                textViewOrigen.text = "Origen: ${comida.strArea}"
                Glide.with(itemView.context).load(comida.strMealThumb).into(imageViewThumbnail)
            }
        }

    }
}
