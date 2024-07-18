package com.example.semestral.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.semestral.R
import com.example.semestral.models.Recipe
import com.squareup.picasso.Picasso

class RecyclerViewRecipe(private val recipes: List<Recipe>) :
    RecyclerView.Adapter<RecyclerViewRecipe.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagenR: ImageView = view.findViewById(R.id.imagenR)
        val tituloR: TextView = view.findViewById(R.id.tituloR)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_recipe, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.tituloR.text = recipe.strMeal
        Picasso.get().load(recipe.strMealThumb).into(holder.imagenR)
    }

    override fun getItemCount() = recipes.size
}
