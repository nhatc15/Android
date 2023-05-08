package com.myapp.newsapp.data.mapper

import com.myapp.newsapp.data.local.db.entities.ArticleEntity
import com.myapp.newsapp.data.model.ArticleModel
import com.myapp.newsapp.data.network.firebase.model.FirebaseFavouriteNew
import com.myapp.newsapp.presentation.model.Article


object NewsMapper {

    fun transformTo(source: List<ArticleModel>): List<Article> {
        return source.map {
            Article(
                author = it.author ?: "",
                content = it.content ?: "",
                description = it.description ?: "",
                publishedAt = it.publishedAt ?: "",
                source = it.source?.name ?: "",
                title = it.title ?: "",
                url = it.url ?: "",
                urlToImage = it.urlToImage ?: ""
            )
        }
    }

    fun mapFromEntity(entity: List<ArticleEntity>): List<Article> {
        return entity.map {
            Article(
                author = it.author,
                content = it.content,
                description = it.description,
                publishedAt = it.publishedAt,
                source = it.source,
                title = it.title,
                url = it.url,
                urlToImage = it.urlToImage
            )
        }
    }

    fun mapToEntity(model: List<Article>, category: String):List<ArticleEntity>{
        return model.map {
            ArticleEntity(
                author = it.author,
                content = it.content,
                description = it.description,
                publishedAt = it.publishedAt,
                source = it.source,
                title = it.title,
                url = it.url,
                urlToImage = it.urlToImage,
                category = category
            )
        }
    }
    fun toFireBase(article: Article): FirebaseFavouriteNew{
        return FirebaseFavouriteNew(
                uid = article.uid,
                author = article.author,
                content = article.content,
                description = article.description,
                publishedAt = article.publishedAt,
                source = article.source,
                title = article.title,
                url = article.url,
                urlToImage = article.urlToImage
        )
    }
    fun fromFireBase(articles: List<FirebaseFavouriteNew>): List<Article>{
        try {
            return articles.map {
                Article(
                    uid = it.uid,
                    author = it.author,
                    content = it.content,
                    description = it.description,
                    publishedAt = it.publishedAt,
                    source = it.source,
                    title = it.title,
                    url = it.url,
                    urlToImage = it.urlToImage,
                )
            }
        }catch (ex: Exception){
            return emptyList()
        }
    }
}