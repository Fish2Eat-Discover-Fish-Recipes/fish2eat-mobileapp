package com.dicoding.fish2eat.ui.home

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dicoding.fish2eat.BuildConfig
import com.dicoding.fish2eat.databinding.FragmentHomeBinding
import com.dicoding.fish2eat.application.data.repository.FishRepository
import com.dicoding.fish2eat.application.data.response.Fish
import com.yalantis.ucrop.UCrop
import kotlinx.coroutines.launch
import org.tensorflow.lite.Interpreter
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var currentPhotoPath: String? = null
    private lateinit var interpreter: Interpreter

    private val labels by lazy { loadLabels() }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val cameraPermission = permissions[Manifest.permission.CAMERA] ?: false
            val storagePermission = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false


        }


    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { startUCropActivity(it) } ?: Toast.makeText(
                context,
                "No image selected",
                Toast.LENGTH_SHORT
            ).show()
        }


    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                currentPhotoPath?.let { path ->
                    val file = File(path)
                    val uri = Uri.fromFile(file)
                    startUCropActivity(uri)
                }
            } else {
                Toast.makeText(context, "Image capture failed", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupClickListeners()

        loadModel()

        return binding.root
    }

    private fun loadModel() {
        try {
            val assetFileDescriptor = requireContext().assets.openFd("ml/fish2eat_model.tflite")
            val fileInputStream = assetFileDescriptor.createInputStream()
            val fileChannel = fileInputStream.channel
            val startOffset = assetFileDescriptor.startOffset
            val declaredLength = assetFileDescriptor.declaredLength
            val modelBuffer =
                fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
            interpreter = Interpreter(modelBuffer)
        } catch (e: Exception) {
            showToast("Error loading model: ${e.message}")
        }
    }

    private fun showToast(message: String) {
        context?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupClickListeners() {
        binding.uploadButton.setOnClickListener {
            permissionLauncher.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
            galleryLauncher.launch("image/*")
        }

        binding.captureButton.setOnClickListener {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            )
            openCamera()
        }
    }

    private fun openCamera() {
        val photoFile = createImageFile()
        val photoUri = FileProvider.getUriForFile(
            requireContext(),
            "${BuildConfig.APPLICATION_ID}.provider",
            photoFile
        )
        currentPhotoPath = photoFile.absolutePath
        cameraLauncher.launch(photoUri)
    }

    private fun checkPermissions(): Boolean {
        val cameraPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        val storagePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        return cameraPermission && storagePermission
    }

    private fun requestPermissions() {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        )
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(System.currentTimeMillis())
        val storageDir = requireContext().cacheDir
        return File.createTempFile("IMG_${timeStamp}_", ".jpg", storageDir)
    }

    private fun startUCropActivity(uri: Uri) {
        val destinationUri = Uri.fromFile(File(requireContext().cacheDir, "cropped_image.jpg"))
        UCrop.of(uri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(800, 800)
            .start(requireContext(), this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data!!)
            resultUri?.let {
                handlePrediction(it)
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            showToast("Crop error: ${cropError?.message}")
        }
    }

    private fun handlePrediction(imageUri: Uri) {
        try {
            val bitmap =
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 128, 128, true)

            val output = FloatArray(15)
            classifyImage(resizedBitmap, output)

            val predictedFishClass = labels[output.indexOfFirst { it == output.maxOrNull() }]
            val confidence = output.maxOrNull() ?: 0f

            Log.d(
                "PredictionOutput",
                "Predicted Class: ${predictedFishClass}, Confidence: $confidence"
            )

            // Tentukan ambang batas untuk akurasi
            val accuracyThreshold = 0.5f

            // Jika akurasi lebih rendah dari ambang batas, tampilkan pemberitahuan
            if (confidence < accuracyThreshold) {
                showToast("Model tidak yakin ini adalah ikan. Coba gambar lain.")
                return
            }

            lifecycleScope.launch {
                try {
                    val fishDetailResponse = FishRepository().getFishInfo(predictedFishClass)

                    navigateToFishDetail(fishDetailResponse, imageUri)
                } catch (e: Exception) {
                    showToast("Error fetching fish details: ${e.message}")
                }
            }

        } catch (e: Exception) {
            showToast("Error during prediction: ${e.message}")
        }
    }

    private fun classifyImage(bitmap: Bitmap, output: FloatArray) {
        val inputBuffer = convertImageToByteBuffer(bitmap)
        val outputBuffer = Array(1) { FloatArray(15) }
        interpreter.run(inputBuffer, outputBuffer)
        System.arraycopy(outputBuffer[0], 0, output, 0, 15)
    }

    private fun convertImageToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(128 * 128 * 3 * 4)
        byteBuffer.order(ByteOrder.nativeOrder())

        val pixels = IntArray(128 * 128)
        bitmap.getPixels(pixels, 0, 128, 0, 0, 128, 128)

        for (pixel in pixels) {
            byteBuffer.putFloat(((pixel shr 16) and 0xFF) / 255.0f)
            byteBuffer.putFloat(((pixel shr 8) and 0xFF) / 255.0f)
            byteBuffer.putFloat((pixel and 0xFF) / 255.0f)
        }

        return byteBuffer
    }

    private fun loadLabels(): List<String> {
        return try {
            requireContext().assets.open("labels.txt").bufferedReader().use { it.readLines() }
        } catch (e: Exception) {
            showToast("Error loading labels: ${e.message}")
            emptyList()
        }
    }

    private fun navigateToFishDetail(fish: Fish, imageUri: Uri) {
        val action = HomeFragmentDirections.actionHomeToFishDetail(
            fish.name,
            fish.scientificName,
            fish.habitat,
            fish.description,
            fish.imageURL,
            imageUri.toString()

        )

        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (::interpreter.isInitialized) {
            interpreter.close()
        }
    }
}