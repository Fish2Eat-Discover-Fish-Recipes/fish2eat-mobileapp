package com.dicoding.fish2eat.ui.Recipe

sealed class RecipeResult<out T> {
    data class Success<out T>(val data: T) : RecipeResult<T>()
    data class Error(val message: String) : RecipeResult<Nothing>()
}