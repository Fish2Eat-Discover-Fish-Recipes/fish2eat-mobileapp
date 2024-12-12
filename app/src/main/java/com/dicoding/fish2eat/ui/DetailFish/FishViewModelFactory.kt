package com.dicoding.fish2eat.ui.DetailFish

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.fish2eat.application.data.repository.FishRepository

class FishViewModelFactory(private val repository: FishRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FishViewModel::class.java)) {
            return FishViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}