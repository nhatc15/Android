package com.myapp.newsapp.presentation.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.myapp.newsapp.R
import com.myapp.newsapp.data.local.preferences.SharedPreferenceHelper
import com.myapp.newsapp.databinding.FragmentHomeBinding
import com.myapp.newsapp.presentation.base.BaseFragment
import com.myapp.newsapp.presentation.model.Article
import com.myapp.newsapp.presentation.ui.fragment.splash.SplashViewModel
import com.myapp.newsapp.util.Constants.Companion.QUERY_PAGE_SIZE
import com.myapp.newsapp.presentation.ui.adapters.LatestNewsAdapter
import com.myapp.newsapp.presentation.ui.adapters.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate,
) {

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    private val homeViewModel: HomeViewModel by viewModels()

    private val splashViewModel: SplashViewModel by viewModels()


    private lateinit var latestNewsAdapter: LatestNewsAdapter

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun handler() {
    }

    override fun intListenner() {
        latestNewsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("article",it)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_articleFragment,
                bundle
            )
        }
        binding.ivLogin.setOnClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_loginFragment,
                null,
            )
        }
    }

    override fun initUI() {

        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility =
            View.VISIBLE
        if (sharedPreferenceHelper.getIsLoggedIn()) {
            binding.ivLogin.visibility = View.GONE
            binding.ivAccount.visibility = View.VISIBLE
        }else{
            binding.ivLogin.visibility = View.VISIBLE
            binding.ivAccount.visibility = View.GONE
        }

        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        binding.vpCategory.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tbCategory, binding.vpCategory) { tab, position ->
            tab.text = when (position) {
                0 -> "Business"
                1 -> "Entertainment"
                2 -> "General"
                3 -> "Health"
                4 -> "Science"
                5 -> "Sport"
                6 -> "Technology"
                else -> ""
            }
        }.attach()
        latestNewsAdapter = LatestNewsAdapter()
        binding.rvLatestNews.apply {
            adapter = latestNewsAdapter
            layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
            addOnScrollListener(this@HomeFragment.scrollListener)
        }

    }

    override fun observeViewModel() {
        homeViewModel.getNewsState.observe(viewLifecycleOwner) { state ->
            when{
                state.isFailed -> {
                    Log.d(TAG,"Loading data failed")
                    hideProgressBar()
                }
                state.isSuccess -> {
                    state.data?.let{latestNewsAdapter.submitList(it)}
                    val list: MutableList<Article>? = null
                    state.data?.let { list?.addAll(it) }
                    val totalPages = (list?.size ?: 0) / QUERY_PAGE_SIZE + 2
                    isLastPage = homeViewModel.latestNewsPage == totalPages
                    if(isLastPage) {
                        binding.rvLatestNews.setPadding(0,0,0,0)
                    }
                    hideProgressBar()
                }
                state.isLoading ->{
                    Log.d(TAG,"Loading")
                    showProgressBar()
                }
            }
        }

    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }
    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNoErrors = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNoErrors && isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                homeViewModel.getNews()
                splashViewModel.insertLatestNews()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private companion object{
        const val TAG = "Get from Room"
    }
}