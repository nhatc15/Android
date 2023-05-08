package com.myapp.newsapp.presentation.ui.fragment.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myapp.newsapp.R
import com.myapp.newsapp.data.local.preferences.SharedPreferenceHelper
import com.myapp.newsapp.databinding.FragmentProfileBinding
import com.myapp.newsapp.util.Constants
import com.myapp.newsapp.presentation.base.BaseFragment
import com.myapp.newsapp.presentation.model.User
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate,

) {

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    private val profileViewModel: ProfileViewModel by viewModels()


    override fun handler() {
        if(sharedPreferenceHelper.getIsLoggedIn()){
            profileViewModel.getUserInfo()
        }
    }

    override fun intListenner() {
        binding.apply {
            btnLogOut.setOnClickListener{
                sharedPreferenceHelper.setIsLoggedIn(false)
                findNavController().navigate(
                    R.id.action_profileFragment_to_loginFragment
                )
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initUI() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.VISIBLE
        if (!sharedPreferenceHelper.getIsLoggedIn()){
            updateUIWhenNotLoggedIn()
        }else{
            binding.apply {
                btnLogOut.text = Constants.LOG_OUT
                tvUsername.text = Constants.LOADING
                tvEmail.text = Constants.LOADING
            }
        }
    }

    override fun observeViewModel() {
        profileViewModel.getUserInfoState.observe(viewLifecycleOwner){state->
            if(state.isFailed){
                Toast.makeText(requireContext(),"Failed",Toast.LENGTH_LONG).show()
            }else{
                if(state.isSuccess){
                    Log.d(TAG,state.user.toString())
                    binding.apply {
                        val user= state.user
                        user?.let { updateUIWhenGetUserInfo(it) }
                        val bundle = Bundle().apply {
                            putParcelable("user",state.user)
                        }
                        btnEditProfile.setOnClickListener {
                            findNavController().navigate(
                                R.id.action_profileFragment_to_updateUserInfoFragment,
                                bundle
                            )
                        }
                    }
                }else{
                    binding.lnUserInfo.visibility = View.GONE
                }
            }
        }
    }

    private fun updateUIWhenNotLoggedIn(){
        binding.apply {
            loadInfoProgressBar.visibility = View.GONE
            btnLogOut.text = Constants.LOG_IN
            lnUserInfo.visibility = View.GONE
            ivAvatar.visibility = View.INVISIBLE
            tvEmail.visibility = View.INVISIBLE
            tvUsername.text = Constants.LOGGED_IN_REMIND
            btnEditProfile.visibility = View.GONE
        }
    }

    private fun updateUIWhenGetUserInfo(user: User){
        binding.apply {
            lnUserInfo.visibility = View.VISIBLE
            loadInfoProgressBar.visibility = View.GONE
            tvEmail.text = user.email
            val atIndex = user.email.indexOf('@')
            val username = atIndex.let { user.email.substring(0, it) }
            tvUsername.text = username
            tvFirstName.text = user.firstName
            tvLastName.text = user.lastName
            tvBirth.text = user.birth
        }
    }

    private companion object{
        const val TAG = "Profile Fragment"
    }
}