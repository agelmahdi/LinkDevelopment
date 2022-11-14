package com.agelmahdi.linkdevelopment.domain.useCase

import com.agelmahdi.linkdevelopment.util.Resource
import com.agelmahdi.linkdevelopment.domain.model.Article
import com.agelmahdi.linkdevelopment.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow

class GetArticleUseCase(
    private val repository: ArticleRepository
) {
    operator fun invoke(): Flow<Resource<List<Article>>> {
        return repository.getArticles()
    }
}