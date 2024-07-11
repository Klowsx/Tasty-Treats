package com.example.semestral.interfaces;

import com.example.semestral.models.Categoria;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ComidaAPI {

    //Obtener categorias
    @GET("https://www.themealdb.com/api/json/v1/1/categories.php")
    public static Call<Categoria> find(){

        return null;
    }
}
