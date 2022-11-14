package com.agelmahdi.linkdevelopment.data.remote

import com.agelmahdi.linkdevelopment.util.Constants.API_KEY
import com.agelmahdi.linkdevelopment.domain.model.ArticlesData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleAPI {
    @GET("v1/articles")
    suspend fun getArticles(
        @Query("source") source: String?,
        @Query("apiKey") apiKey: String = API_KEY,
    ): Response<ArticlesData>
}