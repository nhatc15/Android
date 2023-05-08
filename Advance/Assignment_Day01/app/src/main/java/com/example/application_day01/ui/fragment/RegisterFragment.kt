package com.example.application_day01.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.application_day01.R
import com.example.application_day01.data.UserState
import com.example.application_day01.databinding.FragmentRegisterBinding
import com.example.application_day01.ui.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel? by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignup.setOnClickListener {
            if (checkInputFields()) {
                viewModel?.register(
                    binding.etEmail.text.toString(),
                    binding.etUsername.text.toString(),
                    binding.etPassword.text.toString()
                )
                viewModel?.registerState?.observe(viewLifecycleOwner) { registerState ->
                    when (registerState) {
                        UserState.Success -> findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        else -> Toast.makeText(requireContext(), "Signup failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun checkInputFields(): Boolean {
        var checkResult: Boolean
        with(binding) {
            if (etEmail.text.isNullOrEmpty()) {
                etEmail.error = "Email can't be empty"
                checkResult = false
            } else {
                checkResult = true
            }

            if (etUsername.text.isNullOrEmpty()) {
                binding.etUsername.error = "Username can't be empty"
                checkResult = false
            } else {
                checkResult = true
            }

            if (etPassword.text.isNullOrEmpty()) {
                etPassword.error = "Password can't be empty"
                checkResult = false
            } else {
                checkResult = true
            }

        }
        return checkResult
    }

}