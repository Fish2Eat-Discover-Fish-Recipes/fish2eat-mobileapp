package com.dicoding.fish2eat.ui.Recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.fish2eat.R
import com.dicoding.fish2eat.application.data.base.ApiClient
import com.dicoding.fish2eat.application.data.model.Recipe
import com.dicoding.fish2eat.application.data.response.RecipesItem
import kotlinx.coroutines.launch

class RecipeListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeListAdapter
    private val recipeList = mutableListOf<Recipe>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_recipe_list, container, false)
        recyclerView = view.findViewById(R.id.recipeRecyclerView)

        // Setup RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = RecipeListAdapter(recipeList) { recipe ->
            // Handle item click (e.g., navigate to RecipeDetailFragment)
            val action = RecipeListFragmentDirections.actionRecipeListFragmentToRecipeDetailFragment(recipe)
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter

        val fishName = arguments?.getString("fishName") ?: "Unknown Fish"


        fetchRecipesForFish(fishName)


        val backButton: View = view.findViewById(R.id.backButton)
        backButton.setOnClickListener {
            findNavController().navigateUp() // Kembali ke halaman sebelumnya
        }

        return view
    }

    private fun fetchRecipesForFish(fishName: String) {
        lifecycleScope.launch {
            try {
                // Panggil API menggunakan suspend function
                val fishDetailResponse = ApiClient.apiService.getFishInfo(fishName)
                val recipes = fishDetailResponse.recipes.map { mapToRecipe(it) }
                recipeList.clear()
                recipeList.addAll(recipes) // Tambahkan resep ke daftar
                adapter.notifyDataSetChanged() // Refresh RecyclerView
            } catch (e: Exception) {
                // Tangani error
                // Anda dapat menghapus atau mengganti Toast dengan penanganan kesalahan lainnya
            }
        }
    }

    private fun mapToRecipe(item: RecipesItem): Recipe {
        return Recipe(
            id = item.id,
            cookMethode = item.cookMethode,
            ingredient = item.ingredient,
            instruction = item.instruction,
            imageURL = item.imageURL,
            fishId = item.fishId
        )
    }
}
