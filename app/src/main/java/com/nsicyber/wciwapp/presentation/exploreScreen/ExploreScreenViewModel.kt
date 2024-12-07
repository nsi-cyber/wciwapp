package com.nsicyber.wciwapp.presentation.exploreScreen


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsicyber.wciwapp.common.ApiResult
import com.nsicyber.wciwapp.data.mapFunctions.toCardViewData
import com.nsicyber.wciwapp.data.remote.response.popularMoviesList.PopularMoviesListResponse
import com.nsicyber.wciwapp.data.remote.response.topRatedShowsList.TopRatedShowsListResponse
import com.nsicyber.wciwapp.data.remote.response.trendingList.TrendingListResponse
import com.nsicyber.wciwapp.domain.useCase.GetPopularMoviesUseCase
import com.nsicyber.wciwapp.domain.useCase.GetTopMoviesUseCase
import com.nsicyber.wciwapp.domain.useCase.GetTopRatedShowsUseCase
import com.nsicyber.wciwapp.domain.useCase.GetTrendingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExploreScreenViewModel @Inject
constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedShowsUseCase: GetTopRatedShowsUseCase,
    private val getTrendingUseCase: GetTrendingUseCase,
    private val getTopMoviesUseCase: GetTopMoviesUseCase
) : ViewModel() {


    private val _uiState = MutableStateFlow(ExploreScreenState())
    val uiState: StateFlow<ExploreScreenState> = _uiState.asStateFlow()


    private var currentPagePopularMovies = mutableStateOf(1)
    private var currentPageTopRatedShows = mutableStateOf(1)
    private var isLoadingPopularMovies = mutableStateOf(false)
    private var isLoadingTopRatedShows = mutableStateOf(false)



    fun onEvent(event: ExploreScreenEvent) {
        when (event) {
            is ExploreScreenEvent.LoadPopularMovies -> getPopularMovies()
            is ExploreScreenEvent.LoadTopRatedShows -> getTopRatedShows()
            is ExploreScreenEvent.LoadTrending -> getTrending()
            is ExploreScreenEvent.LoadTopMovies -> getTopMovies()
            ExploreScreenEvent.LoadExploreScreen -> loadExploreScreen()
        }
    }

    private fun loadExploreScreen() = viewModelScope.launch {

        val popularMoviesFlow =
            getPopularMoviesUseCase(currentPagePopularMovies.value).map { result ->
                when (result) {
                    is ApiResult.Success -> result.data
                    is ApiResult.Error -> null
                }
            }

        val topRatedShowsFlow =
            getTopRatedShowsUseCase(currentPageTopRatedShows.value).map { result ->
                when (result) {
                    is ApiResult.Success -> result.data
                    is ApiResult.Error -> null
                }
            }
        val trendingFlow = getTrendingUseCase().map { result ->
            when (result) {
                is ApiResult.Success -> result.data
                is ApiResult.Error -> null
            }
        }

        val topMoviesFlow = getTopMoviesUseCase(1).map { result ->
            when (result) {
                is ApiResult.Success -> result.data
                is ApiResult.Error -> null
            }
        }

        combine(
            popularMoviesFlow,
            topRatedShowsFlow,
            trendingFlow,
            topMoviesFlow,

            ) { results ->
            val popularMovies = results[0] as PopularMoviesListResponse?
            val topRatedShows = results[1] as TopRatedShowsListResponse?
            val trendingList = results[2] as TrendingListResponse?
            val topMovies = results[3] as PopularMoviesListResponse?

            ExploreScreenState(
                isLoading = false,
                popularMovies = popularMovies?.results?.map { it?.toCardViewData() },
                topRatedShows = topRatedShows?.results?.map { it?.toCardViewData() },
                trendingAll = trendingList?.results?.map { it.toCardViewData() },
                topRatedMovies = topMovies?.results?.map { it?.toCardViewData() },

                )
        }
            .onStart { updateUiState { copy(isLoading = true) } }
            .collect { newState -> _uiState.value = newState }
    }


    private fun getTopRatedShows() {
        if (isLoadingTopRatedShows.value) return
        viewModelScope.launch {
            getTopRatedShowsUseCase(currentPageTopRatedShows.value).onStart {
                isLoadingTopRatedShows.value = true
            }.onEach { result ->
                isLoadingTopRatedShows.value = false
                currentPageTopRatedShows.value++
                when (result) {
                    is ApiResult.Error -> {}
                    is ApiResult.Success -> {
                        val updatedList =
                            updateList(
                                result.data?.results?.map { it?.toCardViewData() } ?: emptyList(),
                                _uiState.value.topRatedShows
                            )
                        updateUiState {
                            copy(topRatedShows = updatedList)
                        }
                    }
                }


            }.launchIn(this)

        }
    }

    private fun getTrending() {
        viewModelScope.launch {
            getTrendingUseCase().onStart {
                updateUiState { copy(isLoading = true) }
            }.onEach { result ->
                updateUiState { copy(isLoading = false) }
                when (result) {
                    is ApiResult.Success -> {
                        val updatedList = updateList(
                            result.data?.results?.map { it.toCardViewData() } ?: emptyList(),
                            _uiState.value.trendingAll
                        )
                        updateUiState {
                            copy(trendingAll = updatedList)
                        }
                    }

                    is ApiResult.Error -> {
                        // Hata durumunu burada ele alabilirsiniz
                    }
                }
            }.launchIn(this)
        }
    }

    private fun getTopMovies() {
        viewModelScope.launch {
            getTopMoviesUseCase(1).onStart {
                updateUiState { copy(isLoading = true) }
            }.onEach { result ->
                updateUiState { copy(isLoading = false) }
                when (result) {
                    is ApiResult.Success -> {
                        val updatedList = updateList(
                            result.data?.results?.map { it?.toCardViewData() } ?: emptyList(),
                            _uiState.value.topRatedMovies
                        )
                        updateUiState {
                            copy(topRatedMovies = updatedList)
                        }
                    }

                    is ApiResult.Error -> {
                        // Hata durumunu burada ele alabilirsiniz
                    }
                }
            }.launchIn(this)
        }
    }

    private fun getPopularMovies() {
        if (isLoadingPopularMovies.value) return
        isLoadingPopularMovies.value = true

        viewModelScope.launch {
            getPopularMoviesUseCase(currentPagePopularMovies.value).onStart {
                isLoadingPopularMovies.value = true
            }.onEach { result ->
                isLoadingPopularMovies.value = false
                currentPagePopularMovies.value++
                when (result) {
                    is ApiResult.Success -> {
                        val updatedList = updateList(
                            result.data?.results?.map { it?.toCardViewData() } ?: emptyList(),
                            _uiState.value.popularMovies
                        )
                        updateUiState {
                            copy(popularMovies = updatedList)
                        }
                    }

                    is ApiResult.Error -> {
                        // Hata durumunu burada ele alabilirsiniz
                    }
                }
            }.launchIn(this)
        }
    }

    private fun updateUiState(update: ExploreScreenState.() -> ExploreScreenState) {
        _uiState.update { it.update() }
    }

    private fun <T> updateList(newItems: List<T>, existingItems: List<T>?): List<T> {
        return existingItems?.toMutableList()?.apply { addAll(newItems) } ?: newItems
    }
}