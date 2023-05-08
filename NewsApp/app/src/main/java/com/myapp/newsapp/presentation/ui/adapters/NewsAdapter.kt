package com.androiddevs.mvvmnewsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.myapp.newsapp.databinding.ArticleItemBinding
import com.myapp.newsapp.presentation.base.BaseAdapter
import com.myapp.newsapp.presentation.model.Article

class NewsAdapter : BaseAdapter<ArticleItemBinding, Article>() {
    override fun bindViewHolder(binding: ArticleItemBinding, item: Article) {
        binding.apply {
            item.also {
                tvSource.text = it.source
                tvTitle.text = it.title
                tvTime.text = it.publishedAt

                Glide.with(root)
                    .load(it.urlToImage)
                    .into(ivImage)
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Int) -> ArticleItemBinding
        get() = { inflater, parent, _ ->
            ArticleItemBinding.inflate(inflater, parent, false)
        }
}










