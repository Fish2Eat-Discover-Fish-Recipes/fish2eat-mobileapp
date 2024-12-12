package com.dicoding.fish2eat.application.data.repository

import com.dicoding.fish2eat.application.data.base.ApiClient.apiService
import com.dicoding.fish2eat.application.data.response.FishDetailResponse
import com.dicoding.fish2eat.application.data.response.RecipeResponse
import com.dicoding.fish2eat.application.data.retrofit.RetrofitInstance

import retrofit2.Response

class RecipeRepository {
    // Menggunakan RetrofitInstance untuk mendapatkan apiService
    private val apiService = RetrofitInstance.api

    suspend fun getRecipesByFishId(fishId: String): Response<RecipeResponse> {
        // Panggil apiService untuk mendapatkan data dari API
        return apiService.getRecipesByFishId(fishId)
    }

    suspend fun getFishDetail(fishName: String): FishDetailResponse {
        return apiService.getFishInfo(fishName)
    }

}
