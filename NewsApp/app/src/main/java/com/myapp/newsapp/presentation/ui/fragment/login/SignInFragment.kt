package com.myapp.newsapp.presentation.ui.fragment.login

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myapp.newsapp.R
import com.myapp.newsapp.data.local.preferences.SharedPreferenceHelper
import com.myapp.newsapp.databinding.FragmentSigninBinding
import com.myapp.newsapp.domain.UserResource
import com.myapp.newsapp.util.CheckInput
import com.myapp.newsapp.util.Constants
import com.myapp.newsapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSigninBinding>(
    FragmentSigninBinding::inflate
){
    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper


    private val signInViewModel: SignInViewModel by viewModels()

    override fun handler() {
    }

    override fun intListenner() {
        if(sharedPreferenceHelper.getIsLoggedIn()){
            findNavController().navigate(
                R.id.action_loginFragment_to_homeFragment,
                null,
            )
        }
        binding.apply {
            tvSignUp.setOnClickListener {
            findNavController().navigate(
                R.id.action_loginFragment_to_signupFragment,
                null,
                )
            }

            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                when{
                    !CheckInput.isEmailValid(email) -> etEmail.error = Constants.EMAIL_INVALID
                    !CheckInput.isPasswordValid(password) -> etPassword.error = Constants.PASSWORD_INVALID
                    else -> {
                        if(cbRememberPassword.isChecked){
                            sharedPreferenceHelper.setEmail(email)
                            sharedPreferenceHelper.setPassword(password)
                        }
                        signInViewModel.signIn(email, password)
                    }
                }
            }
            tvSkip.setOnClickListener {
                findNavController().navigate(
                    R.id.action_loginFragment_to_homeFragment,
                    null
                )
            }
        }
    }

    override fun initUI() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.GONE
        with(binding){
            etEmail.setText(sharedPreferenceHelper.getEmail())
            etPassword.setText(sharedPreferenceHelper.getPassword())
            cbRememberPassword.isChecked = true
        }
    }

    override fun observeViewModel() {
        signInViewModel.signInState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is UserResource.Loading -> Log.d(TAG,Constants.LOADING)
                is UserResource.Success -> {
                    sharedPreferenceHelper.setIsLoggedIn(true)
                    findNavController().navigate(
                        R.id.action_loginFragment_to_homeFragment,
                        null,
                    )
                    sharedPreferenceHelper.setIsLoggedIn(true)
                }
                is UserResource.Failed -> Toast.makeText(requireContext(), resource.message,Toast.LENGTH_LONG).show()
                else -> {}
            }
        }
    }
    private companion object{
        const val TAG = "SignIn Fragment"
    }
}