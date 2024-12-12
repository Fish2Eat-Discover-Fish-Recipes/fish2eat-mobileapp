package com.dicoding.fish2eat.ui.DetailFish

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.fish2eat.application.data.repository.FishRepository
import com.dicoding.fish2eat.application.data.response.Fish
import kotlinx.coroutines.launch

class FishViewModel(private val repository: FishRepository) : ViewModel() {

    private val _fishDetail = MutableLiveData<Fish>()
    val fishDetail: LiveData<Fish> get() = _fishDetail

    fun getFishDetails  (fishClass: String) {
        viewModelScope.launch {
            try {
                val response = repository.getFishInfo(fishClass)
                _fishDetail.postValue(response)
            } catch (e: Exception) {
                Log.e("FishViewModel", "Error fetching fish details", e)
            }
        }
    }
}
