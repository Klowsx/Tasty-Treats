package com.example.semestral.domain

import Repo
import com.example.semestral.data.DataSource
import com.example.semestral.models.Comida
import com.example.semestral.vo.Resource

class RepoImp(private val dataSource: DataSource) : Repo {

    override suspend fun fetchMealDetails(idMeal: String): Resource<Comida> {
        return try {
            val comidaResponse = dataSource.obtenerComidaPorId(idMeal)
            comidaResponse  // Ya devuelve Resource.Success o Resource.Failure directamente desde el DataSource
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

    override suspend fun toggleFavoriteRecipe(comida: Comida) {
        dataSource.toggleFavoriteRecipe(comida)
    }

    override suspend fun fetchFavoriteRecipes(): Resource<List<Comida>> {
        return try {
            dataSource.fetchFavoriteRecipes()
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
}
