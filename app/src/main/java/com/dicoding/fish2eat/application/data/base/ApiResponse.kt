/*package com.dicoding.fish2eat.application.data.base

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String) : ApiResponse<Nothing>()
}

class ApiServiceHandler(private val apiService: ApiService) {

    // Fungsi untuk mendapatkan informasi ikan
    suspend fun getFishInfo(): ApiResponse<Response> {
        return try {
            // Asumsikan `apiService.getFishInfo()` adalah pemanggilan API menggunakan Retrofit
            val response = apiService.getFishInfo()

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
}*/
