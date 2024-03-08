package com.abdullah996.registration.presentation.registrationStepTwo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.abdullah996.registration.databinding.FragmentRegisterStepTwoBinding
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class RegisterStepTwoFragment : Fragment() {
    private var _binding: FragmentRegisterStepTwoBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<RegisterStepTwoFragmentArgs>()

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var imageCapture: ImageCapture
    private lateinit var faceDetector: FaceDetector
    private lateinit var cameraProvider: ProcessCameraProvider

    private val registerStepTwoViewModel: RegisterStepTwoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegisterStepTwoBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        handleScreenStates()
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        requestCameraPermissions()
    }

    private fun requestCameraPermissions() {
        when {
            isCameraPermissionGranted() -> {
                setupCamera()
                setupCameraExecutor()
                setupFaceDetector()
            }
            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                setupCamera()
                setupCameraExecutor()
                setupFaceDetector()
            } else {
                Toast.makeText(requireContext(), "camera is mandatory for this screen", Toast.LENGTH_SHORT).show()
            }
        }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED
    }

    private fun handleScreenStates() {
        lifecycleScope.launch {
            registerStepTwoViewModel.screenState.collectLatest {
                when (it) {
                    RegisterStepTwoViewState.Default -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    RegisterStepTwoViewState.Navigate -> {
                        requireActivity().finish()
                    }
                    is RegisterStepTwoViewState.OnError -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    RegisterStepTwoViewState.ShowLoadingViewState -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setupCamera() {
        lifecycleScope.launch(Dispatchers.Main) {
            startCamera()
        }
    }

    private fun setupCameraExecutor() {
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun setupFaceDetector() {
        val options =
            FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .setMinFaceSize(0.15f)
                .enableTracking()
                .build()

        faceDetector = FaceDetection.getClient(options)
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build()
            lifecycleScope.launch(Dispatchers.Main) {
                preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture,
                )

                val imageAnalysis =
                    ImageAnalysis.Builder().build()
                        .also {
                            it.setAnalyzer(
                                cameraExecutor,
                                SmileAnalyzer(faceDetector, lifecycleScope, onSmileDetected = { smileDetected ->
                                    lifecycleScope.launch(Dispatchers.Main) {
                                        if (smileDetected) {
                                            takePhoto()
                                            it.clearAnalyzer()
                                        }
                                    }
                                }, onFailure = {
                                    lifecycleScope.launch {
                                        registerStepTwoViewModel.onFailure(it)
                                    }
                                }),
                            )
                        }

                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    imageAnalysis,
                )
            } catch (exc: Exception) {
                lifecycleScope.launch {
                    registerStepTwoViewModel.onFailure(exc)
                }
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture

        val photoFile =
            File(
                requireContext().getExternalFilesDirs(Environment.DIRECTORY_PICTURES).first(),
                "${System.currentTimeMillis()}.jpg",
            )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    lifecycleScope.launch {
                        registerStepTwoViewModel.onFailure(exc)
                    }
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    lifecycleScope.launch {
                        try {
                            registerStepTwoViewModel.saveUserImage(photoFile.absolutePath, args.id)
                        } catch (e: Exception) {
                            registerStepTwoViewModel.onFailure(e)
                        } finally {
                            cameraProvider.unbindAll()
                        }
                    }
                }
            },
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
