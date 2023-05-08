package com.myapp.newsapp.presentation.ui.fragment.bookmark

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.myapp.newsapp.R
import com.myapp.newsapp.data.local.preferences.SharedPreferenceHelper
import com.myapp.newsapp.databinding.FragmentBookmarkBinding
import com.myapp.newsapp.util.Constants
import com.myapp.newsapp.presentation.base.BaseFragment
import com.myapp.newsapp.presentation.model.Article
import com.myapp.newsapp.presentation.ui.adapters.BookmarkNewsAdapter
import com.myapp.newsapp.presentation.ui.fragment.article.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookmarkFragment : BaseFragment<FragmentBookmarkBinding>(
    FragmentBookmarkBinding::inflate
) {

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    private val articleViewModel: ArticleViewModel by viewModels()
    private lateinit var mAdapter: BookmarkNewsAdapter
    private lateinit var articles : MutableList<Article>
    override fun handler() {
    }

    override fun intListenner() {
        if (sharedPreferenceHelper.getIsLoggedIn()){
            mAdapter.setOnItemClickListener {
                val bundle = Bundle().apply {
                    putParcelable("article", it)
                }
                findNavController().navigate(
                    R.id.action_bookmarkFragment_to_articleFragment,
                    bundle
                )
            }
        }
    }

    override fun initUI() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.VISIBLE
        if(sharedPreferenceHelper.getIsLoggedIn()){
            bookmarkViewModel.getFavouriteNews()
            mAdapter = BookmarkNewsAdapter()
            binding.rvFavourite.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(requireActivity())
            }
            val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition

                    val article = articles[position]
                    bookmarkViewModel.apply {
                        deleteFavouriteNews(article)
                    }
                    view?.let {
                        Snackbar.make(it, Constants.DELETE_FAVOURITE_SUCCESSFULLY, Snackbar.LENGTH_LONG).apply {
                            setAction("Undo") {
                                articleViewModel.insertFavouriteNews(article)
                            }
                            show()
                        }
                    }
                }
            }

            ItemTouchHelper(itemTouchHelperCallback).apply {
                attachToRecyclerView(binding.rvFavourite)
            }
        }else{
            binding.apply {
                rvFavourite.visibility = View.GONE
                tvLabel.visibility = View.GONE
                tvRemind.visibility = View.VISIBLE
            }
        }



    }

    override fun observeViewModel() {
        bookmarkViewModel.getFavouriteNewsState.observe(viewLifecycleOwner){state->
            when{
                state.isFailed -> {
                    binding.paginationProgressBar.visibility = View.GONE
                }
                state.isSuccess -> {
                    state.data?.let { mAdapter.submitList(it) }
                    Log.d(TAG,"${state.data?.size}")
                    articles = mutableListOf<Article>()
                    state.data?.let { articles.addAll(it) }
                    binding.paginationProgressBar.visibility = View.GONE
                }
                state.isLoading ->{
                    binding.paginationProgressBar.visibility = View.VISIBLE
                }
            }
        }

        bookmarkViewModel.deleteFavouriteState.observe(viewLifecycleOwner){state->
            when{
                state.isFailed -> {
                    Toast.makeText(requireContext(),state.message,Toast.LENGTH_LONG).show()
                }
                state.isSuccess -> {
                    Log.d(TAG,"Delete successfully")
                    bookmarkViewModel.getFavouriteNews()
                }
                state.isLoading ->{
                }
            }
        }
    }

    private companion object{
        const val TAG = "Bookmark Fragment"
    }
}