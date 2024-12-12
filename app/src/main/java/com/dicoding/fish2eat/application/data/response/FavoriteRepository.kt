package com.dicoding.fish2eat.application.data.response

class FavoriteRepository(private val favoriteRecipeDao: FavoriteDao) {
    suspend fun insertFavoriteRecipe(favoriteRecipe: FavoriteRecipe) =
        favoriteRecipeDao.insert(favoriteRecipe)

    suspend fun deleteFavoriteRecipe(recipeId: String) =
        favoriteRecipeDao.deleteFavoriteRecipe(recipeId)

    suspend fun getFavoriteRecipeById(recipeId: String) =
        favoriteRecipeDao.getFavoriteRecipeById(recipeId)

    suspend fun getAllFavoriteRecipes() = favoriteRecipeDao.getAllFavoriteRecipes()
}