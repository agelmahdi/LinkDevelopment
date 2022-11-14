package com.agelmahdi.linkdevelopment.domain.repository

import com.agelmahdi.linkdevelopment.util.Resource
import com.agelmahdi.linkdevelopment.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getArticles(): Flow<Resource<List<Article>>>
}