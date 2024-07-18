package com.example.semestral.fragments

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.semestral.R
import com.example.semestral.conexion.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecetaAdapter(private val context: Context) :
    ListAdapter<String, RecetaAdapter.RecetaViewHolder>(RecetasDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recetag, parent, false)
        return RecetaViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecetaViewHolder, position: Int) {
        val idMeal = getItem(position)
        holder.bind(idMeal)
    }

    inner class RecetaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewReceta: TextView = itemView.findViewById(R.id.Nombrereceta)
        private val imageViewReceta: ImageView = itemView.findViewById(R.id.Recetaimg)
        private val textViewDescripcion: TextView = itemView.findViewById(R.id.recetadescripcion)

        fun bind(idMeal: String) {
            // Realizar la solicitud al API usando Retrofit dentro de un CoroutineScope
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val comidaResponse = RetrofitClient.api.obtenerComidaPorId(idMeal)
                    comidaResponse.meals?.firstOrNull()?.let { comida ->
                        Log.d("com.example.semestral.fragments.RecetaAdapter", "Detalles de la comida: ${comida.strMeal}")

                        // Actualizar la UI en el hilo principal
                        withContext(Dispatchers.Main) {
                            Glide.with(itemView).load(comida.strMealThumb).into(imageViewReceta)
                            textViewReceta.text = comida.strMeal
                            textViewDescripcion.text = comida.strInstructions
                        }
                    } ?: Log.d("com.example.semestral.fragments.RecetaAdapter", "La comida no se encontró.")
                } catch (e: Exception) {
                    Log.e("com.example.semestral.fragments.RecetaAdapter", "Error en la llamada: ${e.message}")
                }
            }
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
