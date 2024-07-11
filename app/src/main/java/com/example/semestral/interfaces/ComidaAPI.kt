package com.example.semestral.interfaces;

import com.example.semestral.models.Categoria;
import com.example.semestral.models.CategoriaResponse

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ComidaAPI {

    //Obtener categorias
    @GET("categories.php")
    suspend fun find(): CategoriaResponse

}
