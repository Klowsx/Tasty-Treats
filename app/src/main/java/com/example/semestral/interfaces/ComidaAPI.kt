package com.example.semestral.interfaces

import com.example.semestral.models.CategoriaResponse
import com.example.semestral.models.Comida
import com.example.semestral.models.ComidaResponse

import retrofit2.http.GET

interface ComidaAPI {

    //Obtener categorias
    @GET("categories.php")
    suspend fun obtenerCategorias(): CategoriaResponse

    @GET("random.php")
    suspend fun obtenerRandom(): ComidaResponse


}
