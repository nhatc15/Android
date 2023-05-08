package com.myapp.newsapp.presentation.ui.fragment.explore

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.adapters.NewsAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myapp.newsapp.R
import com.myapp.newsapp.databinding.FragmentExploreBinding
import com.myapp.newsapp.util.CheckInternetConnection
import com.myapp.newsapp.presentation.base.BaseFragment
import com.myapp.newsapp.presentation.model.Article
import com.myapp.newsapp.util.Constants
import com.myapp.newsapp.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>(
    FragmentExploreBinding::inflate
) {
    @Inject
    lateinit var checkInternetConnection: CheckInternetConnection

    private val exploreViewModel: ExploreViewModel by viewModels()

    lateinit var newsAdapter: NewsAdapter

    private val list: List<Article>? = null

    override fun handler() {

    }

    override fun intListenner() {
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("article", it)
            }
            findNavController().navigate(
                R.id.action_exploreFragment_to_articleFragment,
                bundle
            )
        }
        binding.tvCancel.setOnClickListener {
            list?.let { it1 -> newsAdapter.submitList(it1) }
        }
        var job: Job? = null
        with(binding){
            etSearching.queryHint = Constants.SEARCH_REMIND
            etSearching.isIconified = false
            etSearching.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    job?.cancel()
                    job = MainScope().launch {
                        delay(SEARCH_NEWS_TIME_DELAY)
                        query.let {
                            if(query.isNotEmpty()) {
                                if(checkInternetConnection.check(requireContext())){
                                    exploreViewModel.searchNews(query)
                                }else{
                                    exploreViewModel.searchLocalNews(query)
                                }
                            }
                        }
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    list?.let { newsAdapter.submitList(it) }
                    return false
                }
            })
        }
    }

    override fun initUI() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.VISIBLE
        newsAdapter = NewsAdapter()
        binding.rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireActivity())
            addOnScrollListener(this@ExploreFragment.scrollListener)
        }
    }

    override fun observeViewModel() {
        exploreViewModel.searchNewsState.observe(viewLifecycleOwner) { state ->
            when{
                state.isFailed -> {
                    Log.d(TAG,"Loading data failed")
                    hideProgressBar()
                }
                state.isSuccess -> {
                    state.data?.let { newsAdapter.submitList(it) }
                    val list: MutableList<Article>? = null
                    state.data?.let { list?.addAll(it) }
                    val totalPages = (list?.size ?: 0) / Constants.QUERY_PAGE_SIZE + 2
                    isLastPage = exploreViewModel.searchNewsPage == totalPages
                    if(isLastPage) {
                        binding.rvSearchNews.setPadding(0, 0, 0, 0)
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

    private val scrollListener = object : RecyclerView.OnScrollListener() {
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
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNoErrors && isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                exploreViewModel.searchNews(binding.etSearching.query.toString())
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