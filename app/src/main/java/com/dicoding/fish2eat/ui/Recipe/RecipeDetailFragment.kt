package com.dicoding.fish2eat.ui.Recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dicoding.fish2eat.application.data.model.DatabaseClient
import com.dicoding.fish2eat.application.data.model.Recipe
import com.dicoding.fish2eat.application.data.response.FavoriteRecipe
import com.dicoding.fish2eat.databinding.ActivityDetailRecipeBinding
import kotlinx.coroutines.launch

class RecipeDetailFragment : Fragment() {
    private lateinit var recipe: Recipe
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ActivityDetailRecipeBinding.inflate(inflater, container, false)
// Ambil data resep yang diteruskan melalui Safe Args
        recipe = RecipeDetailFragmentArgs.fromBundle(requireArguments()).recipe
// Set data ke view
        binding.textViewCookMethod.text = recipe.cookMethode
        binding.textViewIngredient.text = recipe.ingredient
        binding.textViewInstruction.text = recipe.instruction
        Glide.with(requireContext())
            .load(recipe.imageURL)
            .into(binding.imageViewRecipe)

        return binding.root
    }
}

