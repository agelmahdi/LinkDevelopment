package com.agelmahdi.linkdevelopment.presentation.states

import com.agelmahdi.linkdevelopment.domain.model.Article

data class ArticleState(
    val articles: List<Article> = emptyList(),
    val message: String = "",
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)
