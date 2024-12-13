package com.dicoding.fish2eat.application.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class FishDetailResponse(
    @field:SerializedName("fish")
    val fish: Fish,
    @field:SerializedName("recipes")
    val recipes: List<RecipesItem>
) : Parcelable

@Parcelize
data class Fish(
    @field:SerializedName("habitat")
    val habitat: String,

    @field:SerializedName("scientificName")
    val scientificName: String,

    @field:SerializedName("imageURL")
    val imageURL: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("id")
    val id: String
) : Parcelable