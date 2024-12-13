package com.dicoding.fish2eat.application.data.repository

import com.dicoding.fish2eat.application.data.base.ApiClient
import com.dicoding.fish2eat.application.data.response.Fish
import com.dicoding.fish2eat.application.data.response.FishDetailResponse
import com.dicoding.fish2eat.application.data.response.PredictionResponse

class FishRepository {

    private val apiService = ApiClient.apiService

    suspend fun predictFishInfo(fileBase64: String): PredictionResponse {
        val requestBody = mapOf("file" to fileBase64)
        return try {
            apiService.predictFishInfo(requestBody)
        } catch (e: Exception) {
            throw Exception("Error during prediction request: ${e.message}")
        }
    }

    suspend fun getFishInfo(predictedFishClass: String): Fish {
        val response = apiService.getFishInfo(predictedFishClass)
        return response.fish
    }


}
