package com.myapp.newsapp.presentation.ui.fragment.signup

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myapp.newsapp.R
import com.myapp.newsapp.databinding.FragmentSignupBinding
import com.myapp.newsapp.domain.UserResource
import com.myapp.newsapp.util.CheckInput
import com.myapp.newsapp.util.Constants
import com.myapp.newsapp.presentation.base.BaseFragment
import com.myapp.newsapp.presentation.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignupBinding>(
    FragmentSignupBinding::inflate
){

    private val signupViewModel: SignUpViewModel by viewModels()

    override fun handler() {
    }

    override fun intListenner() {
        binding.apply {
            btnSignup.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val enterPassword = etEnterPassword.text.toString()
                when {
                    !CheckInput.isEmailValid(email) -> etEmail.error = Constants.EMAIL_INVALID
                    !CheckInput.isPasswordValid(password) -> etPassword.error = Constants.PASSWORD_INVALID
                    password != enterPassword -> etEnterPassword.error = Constants.ENTER_PASSWORD_INVALID
                    else -> {
                        signupViewModel.signUp(
                            User(
                                uid = "",
                                email = email,
                                password = password,
                                firstName = "",
                                lastName = "",
                                birth = ""
                            )
                        )
                    }
                }
            }
            tvSignIn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun initUI() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.GONE
    }

    override fun observeViewModel() {
        signupViewModel.signUpState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is UserResource.Loading ->
                    Log.d(TAG,Constants.LOADING)
                is UserResource.Success -> {
                    Toast.makeText(requireContext(),Constants.SIGN_UP_SUCCESSFUL,Toast.LENGTH_LONG).show()
                }
                is UserResource.Failed -> Toast.makeText(requireContext(), resource.message,Toast.LENGTH_LONG).show()
                else -> {}
            }
        }
    }
    private companion object{
        const val TAG = "SignUp Fragment"
    }
}

