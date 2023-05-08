package com.myapp.newsapp.presentation.ui.fragment.splash

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myapp.newsapp.R
import com.myapp.newsapp.data.local.preferences.SharedPreferenceHelper
import com.myapp.newsapp.databinding.FragmentSplashBinding
import com.myapp.newsapp.util.CheckInternetConnection
import com.myapp.newsapp.presentation.base.BaseFragment
import com.myapp.newsapp.util.Constants.Companion.SPLASH_SCREEN_TIME_DELAY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(
    FragmentSplashBinding::inflate
) {
    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    @Inject
    lateinit var checkInternetConnection: CheckInternetConnection

    private val splashViewModel: SplashViewModel by viewModels()



    override fun intListenner() {
        if(checkInternetConnection.check(requireContext())){
            if (sharedPreferenceHelper.getISGetNewsByCategory()){
                splashViewModel.apply {
                    getNewsByCategory("business")
                    getNewsByCategory("entertainment")
                    getNewsByCategory("general")
                    getNewsByCategory("health")
                    getNewsByCategory("science")
                    getNewsByCategory("sports")
                    getNewsByCategory("technology")
                }
            }
            CoroutineScope(Dispatchers.IO).launch {
                splashViewModel.insertLatestNews()
            }
        }
    }

    override fun initUI() {

        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.GONE
    }

    override fun observeViewModel() {
        splashViewModel.insertNewsState.observe(viewLifecycleOwner){state ->
            if(state.isFailed){
                Log.d(TAG,"Loading data failed")
            }else{
                if(state.isSuccess){
                    Log.d(TAG,"Loading data successful")
                }else{
                    Log.d(TAG,"Loading")
                }
            }
        }

        splashViewModel.newsByCategoryState.observe(viewLifecycleOwner){state ->
            if(state.isFailed){
                Log.d(TAG,"Loading category data failed")
            }else{
                if(state.isSuccess){
                    sharedPreferenceHelper.setIsGetNewByCategory(false)
                    Log.d(TAG,"Loading category data successful")
                }else{
                    Log.d(TAG,"Loading category data")
                }
            }
        }
    }

    override fun handler() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                delay(SPLASH_SCREEN_TIME_DELAY)
                splashViewModel.insertLatestNews()
                if(sharedPreferenceHelper.getISLoggedFirstTime()){
                    findNavController().navigate(
                        R.id.action_splashFragment_to_welcomeFragment,
                        null,
                    )
                }
                else{
                    findNavController().navigate(
                        R.id.action_splashFragment_to_homeFragment,
                        null,
                    )
                }
            }
        }
    }
    private companion object{
        const val TAG = "Load data from API"
    }
}