package com.example.semestral.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.semestral.R

class RecetasGuardadasAdapter(private val context: Context) :
    ListAdapter<String, RecetasGuardadasAdapter.RecetaViewHolder>(RecetasDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recetag, parent, false)
        return RecetaViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecetaViewHolder, position: Int) {
        val receta = getItem(position)
        holder.bind(receta)
    }

    inner class RecetaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewReceta: TextView = itemView.findViewById(R.id.Nombrereceta)

        fun bind(receta: String) {
            textViewReceta.text = receta
        }
    }
}

class RecetasDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
