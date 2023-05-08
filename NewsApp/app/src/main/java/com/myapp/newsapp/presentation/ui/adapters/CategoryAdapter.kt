package com.myapp.newsapp.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.myapp.newsapp.databinding.CategoryArticleItemBinding
import com.myapp.newsapp.presentation.base.BaseAdapter
import com.myapp.newsapp.presentation.model.Article

class CategoryAdapter: BaseAdapter<CategoryArticleItemBinding, Article>() {
    override fun bindViewHolder(binding: CategoryArticleItemBinding, item: Article) {
        binding.apply {
            item.also {
                tvCategoryArticleAuthor.text = it.author
                tvCategoryArticleTitle.text = it.title

                Glide.with(root)
                    .load(it.urlToImage)
                    .into(ivImage)
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Int) -> CategoryArticleItemBinding
        get() = { inflater, parent, _ ->
            CategoryArticleItemBinding.inflate(inflater, parent, false)
        }
}
