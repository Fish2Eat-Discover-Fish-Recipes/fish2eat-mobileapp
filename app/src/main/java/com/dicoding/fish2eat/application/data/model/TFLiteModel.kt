package com.dicoding.fish2eat.application.data.model

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.Interpreter
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class TFLiteModel(context: Context, modelName: String) {

    private var interpreter: Interpreter

    init {
        val modelFile = loadModelFile(context, modelName)
        interpreter = Interpreter(modelFile)
    }

    private fun loadModelFile(context: Context, modelName: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }


    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val buffer = ByteBuffer.allocateDirect(128 * 128 * 3 * 4) //
        buffer.order(ByteOrder.nativeOrder())

        val pixels = IntArray(128 * 128)
        bitmap.getPixels(pixels, 0, 128, 0, 0, 128, 128)

        for (pixel in pixels) {
            buffer.putFloat(((pixel shr 16) and 0xFF) / 255.0f) // Red
            buffer.putFloat(((pixel shr 8) and 0xFF) / 255.0f)  // Green
            buffer.putFloat((pixel and 0xFF) / 255.0f)         // Blue
        }
        return buffer
    }

    fun runModel(bitmap: Bitmap, output: FloatArray) {
        val byteBuffer = convertBitmapToByteBuffer(bitmap)

        val inputArray = Array(1) { FloatArray(128 * 128 * 3) }
        byteBuffer.rewind()
        byteBuffer.asFloatBuffer().get(inputArray[0])

        interpreter.run(inputArray, output)
    }

    fun close() {
        interpreter.close()
    }
}
