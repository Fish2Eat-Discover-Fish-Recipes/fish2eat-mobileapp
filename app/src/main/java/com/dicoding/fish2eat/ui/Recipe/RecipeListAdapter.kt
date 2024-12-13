package com.dicoding.fish2eat.ui.Recipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.fish2eat.R
import com.dicoding.fish2eat.application.data.model.Recipe

class RecipeListAdapter(
    private val recipeList: List<Recipe>,
    private val onItemClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewRecipe: ImageView = itemView.findViewById(R.id.imageViewRecipe)
        val textViewCookMethod: TextView = itemView.findViewById(R.id.textViewCookMethod)
        val textViewIngredient: TextView = itemView.findViewById(R.id.textViewIngredient)

        init {
            itemView.setOnClickListener {
                // Ketika item diklik, panggil itemClickListener dan kirimkan data resep
                val recipe = recipeList[adapterPosition]
                onItemClick(recipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]

        // Mengatur data pada tampilan
        holder.textViewCookMethod.text = recipe.cookMethode
        holder.textViewIngredient.text = recipe.ingredient

        // Menggunakan Glide untuk memuat gambar
        Glide.with(holder.itemView.context)
            .load(recipe.imageURL)
            .placeholder(R.drawable.placeholder_image)
            .into(holder.imageViewRecipe)

        // Klik item untuk detail
        holder.itemView.setOnClickListener { onItemClick(recipe) }
    }

    override fun getItemCount(): Int = recipeList.size
}
