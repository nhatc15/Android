package com.myapp.newsapp.presentation.ui.fragment.viewpager

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapp.newsapp.R
import com.myapp.newsapp.databinding.FragmentCategoryBinding
import com.myapp.newsapp.presentation.base.BaseFragment
import com.myapp.newsapp.presentation.ui.adapters.CategoryAdapter
import com.myapp.newsapp.presentation.ui.fragment.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SportFragment:BaseFragment<FragmentCategoryBinding>(
    FragmentCategoryBinding::inflate
) {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var  mAdapter: CategoryAdapter

    override fun handler() {
        homeViewModel.getNewsByCategory("sports")
    }

    override fun intListenner() {
        mAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("article",it)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_articleFragment,
                bundle
            )
        }
    }

    override fun initUI() {

        mAdapter = CategoryAdapter()
        binding.rvNews.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireActivity())

        }
    }

    override fun observeViewModel() {
        homeViewModel.getNewsByCategoryState.observe(viewLifecycleOwner) { state ->
            when{
                state.isFailed -> {
                    hideProgressBar()
                }
                state.isSuccess -> {
                    state.data?.let{ mAdapter.submitList(it)}
                    hideProgressBar()
                }
                state.isLoading ->{
                    showProgressBar()
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }
}