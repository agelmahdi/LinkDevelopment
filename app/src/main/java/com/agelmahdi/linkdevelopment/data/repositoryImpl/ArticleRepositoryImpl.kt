package com.agelmahdi.linkdevelopment.data.repositoryImpl

import android.content.Context
import com.agelmahdi.linkdevelopment.R
import com.agelmahdi.linkdevelopment.util.Constants
import com.agelmahdi.linkdevelopment.util.Resource
import com.agelmahdi.linkdevelopment.data.remote.ArticleAPI
import com.agelmahdi.linkdevelopment.domain.model.Article
import com.agelmahdi.linkdevelopment.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.util.*
import kotlin.collections.List

class ArticleRepositoryImpl(
    private val context: Context,
    private val api: ArticleAPI,
) : ArticleRepository {
    override fun getArticles(): Flow<Resource<List<Article>>> = flow {
        emit(Resource.Loading())
        try {
            val articles = ArrayList<Article>()
            api.getArticles(Constants.SOURCE1).body()?.articles?.let { articles.addAll(it) }
            api.getArticles(Constants.SOURCE2).body()?.articles?.let { articles.addAll(it) }

            emit(Resource.Success(articles))

        } catch (t: Throwable) {
            when (t) {
                is IOException -> emit(
                    Resource.Error(
                        message = context.getString(R.string.network_failure),
                    )
                )
                else -> emit(
                    Resource.Error(
                        message = context.getString(R.string.somthing_went_wrong),
                    )
                )
            }
        }
    }
}