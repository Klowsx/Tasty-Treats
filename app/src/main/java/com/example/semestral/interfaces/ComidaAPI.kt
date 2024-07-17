package com.example.semestral.interfaces

import com.example.semestral.models.CategoriaResponse
import com.example.semestral.models.Comida
import com.example.semestral.models.ComidaResponse
import com.example.semestral.models.RecipeResponse

import retrofit2.http.GET
import retrofit2.http.Query

interface ComidaAPI {

    //Obtener categorias
    @GET("categories.php")
    suspend fun obtenerCategorias(): CategoriaResponse

    @GET("random.php")
    suspend fun obtenerRandom(): ComidaResponse

    @GET("lookup.php")
    suspend fun obtenerComidaPorId(@Query("i") id: String): ComidaResponse

    @GET("search.php")
    suspend fun  searchRecipes(@Query("s")query: String): ComidaResponse

    @GET("filter.php")
    fun obtenerRecetas(@Query("c") categoria: String): RecipeResponse


}
