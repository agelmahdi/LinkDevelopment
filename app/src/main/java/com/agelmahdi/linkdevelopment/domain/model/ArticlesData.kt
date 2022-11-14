package com.agelmahdi.linkdevelopment.domain.model

data class ArticlesData(
    val articles: List<Article>,
    val sortBy: String,
    val source: String,
    val status: String
)
