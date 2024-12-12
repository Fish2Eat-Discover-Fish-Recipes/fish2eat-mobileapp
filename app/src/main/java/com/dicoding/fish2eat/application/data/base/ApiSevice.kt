package com.dicoding.fish2eat.application.data.base
import com.dicoding.fish2eat.application.data.response.RecipeResponse
import com.dicoding.fish2eat.application.data.response.FishDetailResponse
import com.dicoding.fish2eat.application.data.response.PredictionResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("/predict-fish-info/")
    suspend fun predictFishInfo(@Body requestBody: Map<String, String>): PredictionResponse

    @GET("/fish-info/{fishName}")
    suspend fun getFishInfo(@Path("fishName") fishName: String): FishDetailResponse

   @GET("/fish-info/{fishId}")
    suspend fun getRecipesByFishId(@Path("fishId") fishId: String): Response<RecipeResponse>
}
