/*package com.dicoding.fish2eat.application.data.base

import com.dicoding.fish2eat.application.data.response.FishDetailResponse
import com.dicoding.fish2eat.application.data.response.PredictionResponse
import retrofit2.Response

class ApiServiceHandler(private val apiService: ApiService) {

    // Fungsi untuk mendapatkan informasi ikan
    suspend fun getFishInfo(fishName: String): ApiResponse<FishDetailResponse> {
        return try {
            val response: Response<FishDetailResponse> = apiService.getFishInfo(fishName)

            // Cek jika response sukses
            if (response.isSuccessful && response.body() != null) {
                ApiResponse.Success(response.body()!!)
            } else {
                ApiResponse.Error("Failed to fetch data: ${response.message()}")
            }
        } catch (e: Exception) {
            ApiResponse.Error("An error occurred: ${e.message}")
        }
    }

    // Fungsi untuk prediksi informasi ikan berdasarkan gambar
    suspend fun predictFishInfo(requestBody: Map<String, String>): ApiResponse<PredictionResponse> {
        return try {
            val response: Response<PredictionResponse> = apiService.predictFishInfo(requestBody)

            // Cek jika response sukses
            if (response.isSuccessful && response.body() != null) {
                ApiResponse.Success(response.body()!!)
            } else {
                ApiResponse.Error("Failed to fetch data: ${response.message()}")
            }
        } catch (e: Exception) {
            ApiResponse.Error("An error occurred: ${e.message}")
        }
    }
}
*/