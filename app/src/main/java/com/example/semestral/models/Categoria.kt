package com.example.semestral.models

data class Categoria(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)

data class CategoriaResponse(
    val categories: List<Categoria>
)