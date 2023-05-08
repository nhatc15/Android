package com.myapp.newsapp.presentation.ui.fragment.welcome

import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myapp.newsapp.R
import com.myapp.newsapp.data.local.preferences.SharedPreferenceHelper
import com.myapp.newsapp.databinding.FragmentWelcomeBinding
import com.myapp.newsapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>(
    FragmentWelcomeBinding::inflate
) {
    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    override fun handler() {

    }

    override fun intListenner() {
        binding.btnNext.setOnClickListener {
            findNavController().navigate(
                R.id.action_welcomeFragment_to_loginFragment,
                null,
            )
            sharedPreferenceHelper.setIsLoggedFirstTime(false)
        }
    }

    override fun initUI() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.GONE
    }

    override fun observeViewModel() {

    }

}