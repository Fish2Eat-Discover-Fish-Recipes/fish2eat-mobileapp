package com.dicoding.fish2eat.application.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class RecipeResponse(
@field:SerializedName("recipes")
val recipes: List<RecipesItem>
) : Parcelable

@Parcelize
data class RecipesItem(
    @field:SerializedName("cookMethode")
    val cookMethode: String,

    @field:SerializedName("ingredient")
    val ingredient: String,

    @field:SerializedName("instruction")
    val instruction: String,

    @field:SerializedName("imageURL")
    val imageURL: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("fishId")
    val fishId: String,

    @field:SerializedName("id")
    val id: String,

) : Parcelable