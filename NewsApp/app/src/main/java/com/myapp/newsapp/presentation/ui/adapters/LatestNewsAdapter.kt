package com.myapp.newsapp.presentation.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.myapp.newsapp.databinding.LatestArticleItemBinding
import com.myapp.newsapp.presentation.base.BaseAdapter
import com.myapp.newsapp.presentation.model.Article

class LatestNewsAdapter : BaseAdapter<LatestArticleItemBinding, Article>() {
    @SuppressLint("SetTextI18n")
    override fun bindViewHolder(binding: LatestArticleItemBinding, item: Article) {
        binding.apply {
            item.also {
                tvHotAuthor.text = "by "+ it.author
                tvHotTitle.text = it.title

                Glide.with(root)
                    .load(it.urlToImage)
                    .into(ivHot)
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Int) -> LatestArticleItemBinding
        get() = { inflater, parent, _ ->
            LatestArticleItemBinding.inflate(inflater, parent, false)
        }
}