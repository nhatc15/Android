package com.myapp.newsapp.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.myapp.newsapp.databinding.BookmarkArticleItemBinding
import com.myapp.newsapp.presentation.base.BaseAdapter
import com.myapp.newsapp.presentation.model.Article

class BookmarkNewsAdapter: BaseAdapter<BookmarkArticleItemBinding, Article>() {
    override fun bindViewHolder(binding: BookmarkArticleItemBinding, item: Article) {
        binding.apply {
            item.also {
                tvTime.text = it.publishedAt
                tvSource.text = it.source
                tvTitle.text = it.title

                Glide.with(root)
                    .load(it.urlToImage)
                    .into(ivImage)
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Int) -> BookmarkArticleItemBinding
        get() = { inflater, parent, _ ->
            BookmarkArticleItemBinding.inflate(inflater, parent, false)
        }
}