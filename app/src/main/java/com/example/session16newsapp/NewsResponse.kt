package com.example.session16newsapp

data class NewsResponse(
    val articles: List<Article>
)

data class Article(
    val title: String,
    val description: String
)