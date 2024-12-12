package com.dicoding.fish2eat.ui.favorite

import com.dicoding.fish2eat.application.data.response.FavoriteRecipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.fish2eat.R

class FavoriteAdapter : ListAdapter<FavoriteRecipe, FavoriteAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite)
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewFavorite)
        private val textCookMethod: TextView = itemView.findViewById(R.id.textViewCookMethod)
        private val textIngredient: TextView = itemView.findViewById(R.id.textViewIngredient)

        fun bind(favorite: FavoriteRecipe) {
            textIngredient.text = favorite.ingredient

            Glide.with(itemView.context)
                .load(favorite.imageURL)
                .into(imageView)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteRecipe>() {
            override fun areItemsTheSame(oldItem: FavoriteRecipe, newItem: FavoriteRecipe): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavoriteRecipe, newItem: FavoriteRecipe): Boolean {
                return oldItem == newItem
            }
        }
    }
}
