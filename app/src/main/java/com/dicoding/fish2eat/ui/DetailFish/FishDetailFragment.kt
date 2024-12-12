package com.dicoding.fish2eat.ui.fishdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dicoding.fish2eat.R
import com.dicoding.fish2eat.application.data.base.ApiClient
import com.dicoding.fish2eat.application.data.response.FishDetailResponse
import com.dicoding.fish2eat.databinding.ActivityDetailFishBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FishDetailFragment : Fragment(R.layout.activity_detail_fish) {

    private var _binding: ActivityDetailFishBinding? = null
    private val binding get() = _binding!!

    private val args: FishDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityDetailFishBinding.inflate(inflater, container, false)


        val fishName = args.fishName
        val scientificName = args.scientificName
        val habitat = args.habitat
        val description = args.description
        val imageUrl = args.imageURL
        val imageUri = args.imageUri

        binding.fishNameTextView.text = fishName
        binding.scientificNameTextView.text = scientificName
        binding.fishDescriptionTextView.text = description
        binding.habitatTextView.text = habitat

        Picasso.get()
            .load(imageUrl)
            .into(binding.fishImageView)

       /* if (imageUri.isNotEmpty()) {
            Picasso.get()
                .load(imageUri) // Memuat gambar lokal
                .into(binding.fishImageView)
        }*/

        binding.fishNameTextView.text = fishName

        binding.searchRecipeButton.setOnClickListener {
            fetchFishIdAndNavigate(fishName)
        }

        return binding.root
    }

    private fun fetchFishIdAndNavigate(fishName: String) {
        lifecycleScope.launch {
            try {
                val fishDetailResponse = ApiClient.apiService.getFishInfo(fishName)
                val fishId = fishDetailResponse.fish?.id

                if (fishId != null) {
                    val action = FishDetailFragmentDirections.actionFishDetailToRecipeList(
                        fishId = fishId,
                        fishName = fishName
                    )
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(requireContext(), "Fish ID tidak ditemukan", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Kesalahan jaringan: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
