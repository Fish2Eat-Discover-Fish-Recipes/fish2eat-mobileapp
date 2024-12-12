package com.dicoding.fish2eat.application.data.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.OnConflictStrategy


/*@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes WHERE fishId = :fishId")
    fun getRecipesByFishId(fishId: String): List<RecipeItem>

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): LiveData<List<RecipeItem>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<RecipeItem>)
}*/