package com.example.semestral.models

data class Recipe(
    val idCategory: String,
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)

data class RecipeResponse(
    val meals: List<Recipe>?
)