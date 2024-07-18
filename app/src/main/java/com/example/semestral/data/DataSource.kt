package com.example.semestral.data

import android.content.Context
import android.util.Log
import com.example.semestral.conexion.RetrofitClient
import com.example.semestral.models.Comida
import com.example.semestral.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataSource(private val context: Context) {

    // Obtener detalles de una comida por su ID desde la API
    suspend fun obtenerComidaPorId(idMeal: String): Resource<Comida> {
        return try {
            val comidaResponse = withContext(Dispatchers.IO) {
                RetrofitClient.api.obtenerComidaPorId(idMeal)
            }
            Resource.Success(comidaResponse.meals?.firstOrNull() ?: throw Exception("Comida no encontrada"))
        } catch (e: Exception) {
            Log.e("com.example.semestral.data.DataSource", "Error en la llamada: ${e.message}")
            Resource.Failure(e)
        }
    }

    // Agregar o eliminar una receta de la lista de favoritos
    suspend fun toggleFavoriteRecipe(comida: Comida): Boolean {
        val sharedPreferences = context.getSharedPreferences(
            "com.example.semestral.activities.RecetasGuardadas",
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        val savedRecipes = sharedPreferences.getStringSet("recetas", mutableSetOf())?.toMutableSet() ?: mutableSetOf()

        val idMeal = comida.idMeal

        if (savedRecipes.contains(idMeal)) {
            savedRecipes.remove(idMeal)
        } else {
            savedRecipes.add(idMeal)
        }

        editor.putStringSet("recetas", savedRecipes)
        return editor.commit()  // Commit the changes synchronously
    }

    // Obtener todas las recetas favoritas almacenadas localmente
    suspend fun fetchFavoriteRecipes(): Resource<List<Comida>> {
        val sharedPreferences = context.getSharedPreferences(
            "com.example.semestral.activities.RecetasGuardadas",
            Context.MODE_PRIVATE
        )
        val savedRecipes = sharedPreferences.getStringSet("recetas", mutableSetOf()) ?: mutableSetOf()

        val favoriteRecipes = mutableListOf<Comida>()

        // Obtener detalles de cada receta favorita desde la API (por ejemplo)
        for (idMeal in savedRecipes) {
            try {
                val comidaResponse = withContext(Dispatchers.IO) {
                    RetrofitClient.api.obtenerComidaPorId(idMeal)
                }
                comidaResponse.meals?.firstOrNull()?.let { comida ->
                    favoriteRecipes.add(comida)
                }
            } catch (e: Exception) {
                Log.e("com.example.semestral.data.DataSource", "Error al obtener detalles de la comida: $idMeal, error: ${e.message}")
            }
        }

        return Resource.Success(favoriteRecipes)
    }
}
