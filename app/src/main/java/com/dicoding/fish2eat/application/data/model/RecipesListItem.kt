package com.dicoding.fish2eat.application.data.model

data class RecipesListItem(
    val id: String,
    val cookMethode: String,
    val ingredient: String,
    val instruction: String,
    val imageUrl: String,
    val fishId: String
)