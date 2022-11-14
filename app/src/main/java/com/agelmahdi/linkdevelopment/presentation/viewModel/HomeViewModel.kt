package com.agelmahdi.linkdevelopment.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agelmahdi.linkdevelopment.util.Resource
import com.agelmahdi.linkdevelopment.domain.useCase.GetArticleUseCase
import com.agelmahdi.linkdevelopment.presentation.states.ArticleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase
) : ViewModel() {
    private val _stateFlow = MutableStateFlow(ArticleState())
    val stateFlow: StateFlow<ArticleState> = _stateFlow

    private var articleJob: Job? = null

    fun getArticles() {
        articleJob?.cancel()
        articleJob = viewModelScope.launch(Dispatchers.Main) {
            getArticleUseCase().onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _stateFlow.value = stateFlow.value.copy(
                            isLoading = true,
                        )
                    }
                    is Resource.Success -> {
                        _stateFlow.value = stateFlow.value.copy(
                            message = result.message ?: "",
                            articles = result.data ?: emptyList(),
                        )
                    }
                    is Resource.Error -> {
                        _stateFlow.value = stateFlow.value.copy(
                            message = result.message ?: "",
                            isLoading = false,
                            isError = true
                        )
                    }
                }

            }.launchIn(this)
        }
    }
}