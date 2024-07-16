package com.example.semestral.fragments

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.semestral.R

import com.example.semestral.models.Categoria

class RecyclerViewAdapter(private val categorias: List<Categoria>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_categorias, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.bind(categoria)
    }

    override fun getItemCount(): Int {
        return categorias.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idView: TextView = itemView.findViewById(R.id.item_number)
        private val contentView: TextView = itemView.findViewById(R.id.content)

        fun bind(categoria: Categoria) {
            idView.text = categoria.idCategory
            contentView.text = categoria.strCategory
        }
    }
}