package com.agelmahdi.linkdevelopment.di

import android.content.Context
import com.agelmahdi.linkdevelopment.R
import com.agelmahdi.linkdevelopment.util.Constants
import com.agelmahdi.linkdevelopment.data.remote.ArticleAPI
import com.agelmahdi.linkdevelopment.data.repositoryImpl.ArticleRepositoryImpl
import com.agelmahdi.linkdevelopment.domain.repository.ArticleRepository
import com.agelmahdi.linkdevelopment.domain.useCase.GetArticleUseCase
import com.agelmahdi.linkdevelopment.presentation.NavData
import com.agelmahdi.linkdevelopment.presentation.adapter.NavAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ArticleModule {


    @Provides
    @Singleton
    fun provideNavData(@ApplicationContext context: Context): ArrayList<NavData> {
        val navDataList: ArrayList<NavData> = ArrayList()
        val nData = context.resources.getStringArray(R.array.navigation)
        for (i in nData.indices) {
            val navigateData = NavData(nData[i], Constants.icons[i], false)
            navDataList.add(navigateData)
        }
        return navDataList
    }

    @Provides
    @Singleton
    fun provideNavAdapter(@ApplicationContext context: Context): NavAdapter {
        return NavAdapter(provideNavData(context))
    }

    @Provides
    @Singleton
    fun provideArticlesUseCase(repository: ArticleRepository): GetArticleUseCase {
        return GetArticleUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideArticleRepository(
        @ApplicationContext context: Context,
        api: ArticleAPI
    ): ArticleRepository {
        return ArticleRepositoryImpl(context,api)
    }

    @Provides
    @Singleton
    fun provideArticleAPI(): ArticleAPI {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ArticleAPI::class.java)
    }
}