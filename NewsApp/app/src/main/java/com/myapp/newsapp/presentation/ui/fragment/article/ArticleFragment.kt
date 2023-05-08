package com.myapp.newsapp.presentation.ui.fragment.article

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.myapp.newsapp.R
import com.myapp.newsapp.data.local.preferences.SharedPreferenceHelper
import com.myapp.newsapp.databinding.FragmentArticleBinding
import com.myapp.newsapp.domain.UserResource
import com.myapp.newsapp.util.Constants
import com.myapp.newsapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ArticleFragment: BaseFragment<FragmentArticleBinding>(
    FragmentArticleBinding::inflate
) {

    @Inject
    lateinit var sharedPreferenceHelper: SharedPreferenceHelper

    private val articleViewModel: ArticleViewModel by viewModels()
    private val args: ArticleFragmentArgs by navArgs()

    override fun handler() {
    }

    override fun intListenner() {
        binding.btnBookmark.setOnClickListener {
            if(sharedPreferenceHelper.getIsLoggedIn()){
                articleViewModel.insertFavouriteNews(args.article)
            }else{
                Toast.makeText(requireContext(),Constants.LOG_IN_TO_ADD_TO_FAVOURITE,Toast.LENGTH_LONG).show()
            }

        }
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.VISIBLE
        }
        binding.btnShare.setOnClickListener {
            Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, args.article.url)

                try {
                    startActivity(Intent.createChooser(this, "Select an action"))
                } catch (ex: Exception) {
                    Toast.makeText(requireContext(),ex.message,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initUI() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.GONE
        val article = args.article
        Glide.with(this).load(article.urlToImage).into(binding.ivArticle)
        binding.apply {
            tvArticleSource.text = article.source
            tvArticleTime.text = "Updated "+article.publishedAt
            tvArticleAuthor.text = "by "+article.author+", "
            tvArticleTitle.text = article.title
            tvArticleDescription.text = article.description
            tvArticleContent.text = article.content
            tvArticleUrl.text = article.url
        }
    }

    override fun observeViewModel() {
        articleViewModel.favouriteState.observe(viewLifecycleOwner){ state->
            when (state) {
                is UserResource.Loading ->
                    Log.d(TAG,"Loading")
                is UserResource.Success -> {
                    Toast.makeText(requireContext(),Constants.ADD_SUCCESSFUL,Toast.LENGTH_SHORT).show()
                }
                is UserResource.Failed ->
                    Log.d(TAG,state.toString())
                else -> {}
            }
        }
    }

    private companion object{
        const val TAG = "Article Fragment"
    }

}