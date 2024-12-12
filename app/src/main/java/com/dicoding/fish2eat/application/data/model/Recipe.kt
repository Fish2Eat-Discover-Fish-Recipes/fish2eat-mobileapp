package com.dicoding.fish2eat.application.data.model

import java.io.Serializable

data class Recipe(
    val id: String,
    val cookMethode: String,
    val ingredient: String,
    val instruction: String,
    val imageURL: String,
    val fishId: String
) : Serializable