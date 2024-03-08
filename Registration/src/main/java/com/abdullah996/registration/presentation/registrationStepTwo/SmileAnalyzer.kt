package com.abdullah996.registration.presentation.registrationStepTwo

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.lifecycle.LifecycleCoroutineScope
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SmileAnalyzer(
    private val faceDetector: FaceDetector,
    private val lifecycleScope: LifecycleCoroutineScope,
    private val onSmileDetected: (Boolean) -> Unit,
    private val onFailure: (Exception) -> Unit,
) : ImageAnalysis.Analyzer {
    @OptIn(ExperimentalGetImage::class)
    override fun analyze(image: ImageProxy) {
        lifecycleScope.launch(Dispatchers.Main) {
            val mediaImage = image.image
            if (mediaImage != null) {
                val inputImage =
                    InputImage.fromMediaImage(
                        mediaImage,
                        image.imageInfo.rotationDegrees,
                    )

                faceDetector.process(inputImage)
                    .addOnSuccessListener { faces ->
                        val smilingFaceDetected = faces.any { (it.smilingProbability ?: 0f) >= 0.5 }
                        onSmileDetected(smilingFaceDetected)
                    }
                    .addOnFailureListener {
                        onFailure(it)
                    }
                    .addOnCompleteListener {
                        image.close()
                    }
            }
        }
    }
}
