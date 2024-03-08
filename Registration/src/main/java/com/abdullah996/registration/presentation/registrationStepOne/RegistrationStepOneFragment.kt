package com.abdullah996.registration.presentation.registrationStepOne

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.abdullah996.registration.databinding.FragmentRegistrationStepOneBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationStepOneFragment : Fragment() {
    private var _binding: FragmentRegistrationStepOneBinding? = null

    private val binding get() = _binding!!

    private val registerStepOneViewModel: RegisterStepOneViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegistrationStepOneBinding.inflate(inflater, container, false)
        binding.viewModel = registerStepOneViewModel
        binding.btnRegister.setOnClickListener {
            registerStepOneViewModel.registerUser()
        }
        binding.lifecycleOwner = this
        handleScreenStates()
        return binding.root
    }

    private fun handleScreenStates()  {
        lifecycleScope.launch {
            registerStepOneViewModel.screenState.collectLatest {
                when (it) {
                    RegisterStepOneViewState.Default -> {
                        binding.progressBar.visibility = View.GONE
                    }
                    is RegisterStepOneViewState.Navigate -> {
                        Toast.makeText(requireContext(), "navigate", Toast.LENGTH_SHORT).show()
                        /***
                         * navigate
                         */
                    }
                    is RegisterStepOneViewState.OnError -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    RegisterStepOneViewState.ShowLoadingViewState -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
