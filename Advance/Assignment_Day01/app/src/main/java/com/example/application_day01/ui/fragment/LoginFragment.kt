package com.example.application_day01.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.application_day01.R
import com.example.application_day01.data.UserState
import com.example.application_day01.databinding.FragmentLoginBinding
import com.example.application_day01.ui.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel? by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
        for(i in 1..1000){
            Log.d("nhatnv", "onCreateView in 35 : $i")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            if (checkInputFields()) {

                viewModel?.login(
                    binding.etUsername.text.toString(),
                    binding.etPassword.text.toString()
                )
                viewModel?.loginState?.observe(viewLifecycleOwner) { loginState ->
                    when (loginState) {
                        UserState.Success -> findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        else -> Toast.makeText(requireContext(), "Sign in failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.btnSignup.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun checkInputFields(): Boolean {
        var checkResult: Boolean
        with(binding) {
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
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}