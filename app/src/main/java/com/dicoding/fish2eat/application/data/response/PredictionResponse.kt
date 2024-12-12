package com.dicoding.fish2eat.application.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PredictionResponse(
    @field:SerializedName("prediction")
    val prediction: Prediction
) : Parcelable

@Parcelize
data class Prediction(
    @field:SerializedName("confidence")
    val confidence: String,

    @field:SerializedName("class")
    val jsonMemberClass: String
) : Parcelable