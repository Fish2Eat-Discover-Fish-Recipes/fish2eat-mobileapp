package com.dicoding.fish2eat.ui.Recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dicoding.fish2eat.R
import com.dicoding.fish2eat.application.data.model.Recipe
import com.dicoding.fish2eat.databinding.ActivityDetailRecipeBinding


class RecipeDetailFragment : Fragment() {
    private lateinit var recipe: Recipe
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ActivityDetailRecipeBinding.inflate(inflater, container, false)

        recipe = RecipeDetailFragmentArgs.fromBundle(requireArguments()).recipe

        binding.textViewCookMethod.text = recipe.cookMethode
        binding.textViewIngredient.text = recipe.ingredient
        binding.textViewInstruction.text = recipe.instruction
        Glide.with(requireContext())
            .load(recipe.imageURL)
            .into(binding.imageViewRecipe)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp() // Kembali ke halaman sebelumnya
        }

        binding.homeButton.setOnClickListener {
            findNavController().navigate(R.id.action_recipeDetailFragment_to_navigation_home)  // Navigasi ke HomeFragment
        }


        (activity as? AppCompatActivity)?.supportActionBar?.hide()

        return binding.root
    }
}

