package com.dicoding.fish2eat.application.data.response

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_recipes")
data class FavoriteRecipe(
    @PrimaryKey
    val id: String,
    val cookMethode: String,
    val ingredient: String,
    val instruction: String,
    val imageURL: String,
    val fishId: String
)

