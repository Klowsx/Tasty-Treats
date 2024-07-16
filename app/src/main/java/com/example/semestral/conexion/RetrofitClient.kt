package com.example.semestral.conexion

import com.example.semestral.interfaces.ComidaAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val api: ComidaAPI = retrofit.create(ComidaAPI::class.java)
}

