package com.dicoding.fish2eat.application.data.response

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteRecipe: FavoriteRecipe)

    @Query("SELECT * FROM favorite_recipes WHERE id = :recipeId")
    suspend fun getFavoriteRecipeById(recipeId: String): FavoriteRecipe?

    @Query("DELETE FROM favorite_recipes WHERE id = :recipeId")
    suspend fun deleteFavoriteRecipe(recipeId: String)

    @Query("SELECT * FROM favorite_recipes")
    suspend fun getAllFavoriteRecipes(): List<FavoriteRecipe>
}