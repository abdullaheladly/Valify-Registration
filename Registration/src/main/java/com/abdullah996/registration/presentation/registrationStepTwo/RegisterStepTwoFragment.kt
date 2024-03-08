package com.abdullah996.registration.presentation.registrationStepTwo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.abdullah996.registration.R
import com.abdullah996.registration.databinding.FragmentRegisterStepTwoBinding
import com.abdullah996.registration.presentation.registrationStepOne.RegisterStepOneViewModel
import com.abdullah996.registration.presentation.registrationStepOne.RegisterStepOneViewState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterStepTwoFragment : Fragment() {

    private var _binding: FragmentRegisterStepTwoBinding? = null
    private val binding get() = _binding!!

    private val args by  navArgs<RegisterStepTwoFragmentArgs>()

    private val registerStepTwoViewModel:RegisterStepTwoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentRegisterStepTwoBinding.inflate(layoutInflater,container,false)
        binding.lifecycleOwner=this

        return binding.root
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}