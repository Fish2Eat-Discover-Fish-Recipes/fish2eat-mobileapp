package com.dicoding.fish2eat.ui.Recipe

/*import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import com.dicoding.fish2eat.application.data.model.Recipe
import com.dicoding.fish2eat.application.data.repository.RecipeRepository
import com.dicoding.fish2eat.application.data.response.FavoriteRepository
import kotlinx.coroutines.launch

class RecipeDetailViewModel(private val recipeRepository: RecipeRepository,
                            private val favoriteRecipeRepository: FavoriteRepository) : ViewModel() {

    private val _recipe = MutableLiveData<Resource<Recipe>>()
    val recipe: LiveData<Resource<Recipe>> get() = _recipe

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun getRecipeDetail(recipeId: String) {
        viewModelScope.launch {
            _recipe.value = Resource.Loading()
            try {
                val recipe = recipeRepository.getRecipesByFishId(recipeId)
                _recipe.value = Resource.Success(recipe)
                _isFavorite.value = favoriteRecipeRepository.isFavorite(recipeId)
            } catch (e: Exception) {
                _recipe.value = Resource.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }

    fun toggleFavorite(recipeId: String) {
        viewModelScope.launch {
            val isCurrentlyFavorite = favoriteRecipeRepository.isFavorite(recipeId)
            if (isCurrentlyFavorite) {
                favoriteRecipeRepository.removeFromFavorites(recipeId)
            } else {
                favoriteRecipeRepository.addToFavorites(recipeId)
            }
            _isFavorite.value = !isCurrentlyFavorite
        }
    }
}

 */