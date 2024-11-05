package com.nsicyber.wciwapp.presentation.searchScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsicyber.wciwapp.common.ApiResult
import com.nsicyber.wciwapp.data.remote.response.movieNowPlayingList.MovieNowPlayingListResponse
import com.nsicyber.wciwapp.data.remote.response.movieNowPlayingList.toCardViewData
import com.nsicyber.wciwapp.data.remote.response.searchResultList.toCardViewData
import com.nsicyber.wciwapp.data.remote.response.showOnAirList.ShowOnAirListResponse
import com.nsicyber.wciwapp.data.remote.response.showOnAirList.toCardViewData
import com.nsicyber.wciwapp.domain.model.CardViewData
import com.nsicyber.wciwapp.domain.useCase.GetMovieNowPlayingListUseCase
import com.nsicyber.wciwapp.domain.useCase.GetSearchResultsUseCase
import com.nsicyber.wciwapp.domain.useCase.GetShowOnAirListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
class SearchScreenViewModel @Inject
constructor(
    private val getSearchResultsUseCase: GetSearchResultsUseCase,
    private val getShowOnAirListUseCase: GetShowOnAirListUseCase,
    private val getMovieNowPlayingListUseCase: GetMovieNowPlayingListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchScreenState())
    val uiState: StateFlow<SearchScreenState> get() = _uiState.asStateFlow()


    private var isLoadingSearch = mutableStateOf(false)

    private var searchJob: Job? = null


    private var currentPageMovieNowPlaying = mutableStateOf(1)
    private var isLoadingMovieNowPlaying = mutableStateOf(false)
    private var currentPageShowOnAir = mutableStateOf(1)
    private var isLoadingShowOnAir = mutableStateOf(false)


    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            SearchScreenEvent.LoadSearchScreen -> {
                loadSearchScreen()
            }

            is SearchScreenEvent.Search -> {
                updateUiState { copy(searchQuery = event.query) }
                if (event.query.length >= 3)
                    searchWithDebounce(event.query)
                else if (event.query.isEmpty())
                    setStateEmpty()
            }

            SearchScreenEvent.SetStateEmpty -> {
                setStateEmpty()
            }

            SearchScreenEvent.GetMovieNowPlayingList -> getMovieNowPlayingList()
            SearchScreenEvent.GetShowOnAirList -> getShowOnAirList()
        }
    }



    private fun getMovieNowPlayingList() {
        if (isLoadingMovieNowPlaying.value) return

        viewModelScope.launch {
            getMovieNowPlayingListUseCase(currentPageMovieNowPlaying.value).onStart {
                isLoadingMovieNowPlaying.value = true
            }.onEach { result ->
                isLoadingMovieNowPlaying.value = false
                currentPageMovieNowPlaying.value++
                when (result) {
                    is ApiResult.Success -> {
                        val updatedList = updateList(
                            result.data?.results?.map { it?.toCardViewData() } ?: emptyList(),
                            _uiState.value.movieNowPlayingList
                        )
                        updateUiState {
                            copy(movieNowPlayingList = updatedList)
                        }
                    }

                    is ApiResult.Error -> {
                        // Hata durumunu burada ele alabilirsiniz
                    }
                }
            }.launchIn(this)
        }
    }

    private fun getShowOnAirList() {
        if (isLoadingShowOnAir.value) return

        viewModelScope.launch {
            getShowOnAirListUseCase(currentPageShowOnAir.value).onStart {
                isLoadingShowOnAir.value = true
            }.onEach { result ->
                isLoadingShowOnAir.value = false
                currentPageShowOnAir.value++
                when (result) {
                    is ApiResult.Success -> {
                        val updatedList = updateList(
                            result.data?.results?.map { it?.toCardViewData() } ?: emptyList(),
                            _uiState.value.showOnAirList
                        )
                        updateUiState {
                            copy(showOnAirList = updatedList)
                        }
                    }

                    is ApiResult.Error -> {
                        // Hata durumunu burada ele alabilirsiniz
                    }
                }
            }.launchIn(this)
        }
    }


    private fun loadSearchScreen() = viewModelScope.launch {

        val movieNowPlayingListFlow = getMovieNowPlayingListUseCase(1).map { result ->
            when (result) {
                is ApiResult.Success -> result.data
                is ApiResult.Error -> null
            }
        }

        val showOnAirListFlow = getShowOnAirListUseCase(1).map { result ->
            when (result) {
                is ApiResult.Success -> result.data
                is ApiResult.Error -> null
            }
        }

        combine(
            movieNowPlayingListFlow,
            showOnAirListFlow,
        ) { results ->
            val movieNowPlayingList = results[0] as MovieNowPlayingListResponse?
            val showOnAirList = results[1] as ShowOnAirListResponse?


            SearchScreenState(
                isLoading = false,
                movieNowPlayingList = movieNowPlayingList?.results?.map { it.toCardViewData() },
                showOnAirList = showOnAirList?.results?.map { it.toCardViewData() },
            )
        }
            .onStart { updateUiState { copy(isLoading = true) } }
            .collect { newState -> _uiState.value = newState }
    }


    private fun getSearchResults(query: String) {
        viewModelScope.launch {
            getSearchResultsUseCase(query, 1).onStart {
                isLoadingSearch.value = true


            }.onEach { result ->
                isLoadingSearch.value = false

                when (result) {

                    is ApiResult.Success -> {

                        updateUiState {
                            copy(searchResult = result.data?.results?.map { it.toCardViewData() }
                                ?.filter { it.title?.isNotEmpty() == true })
                        }
                    }

                    is ApiResult.Error -> {

                        // Hata durumunu burada ele alabilirsiniz
                    }
                }
            }.launchIn(this)
        }


    }


    private fun searchWithDebounce(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            getSearchResults(query)
        }
    }

    private fun setStateEmpty() {
        searchJob?.cancel()
        updateUiState { copy(searchResult = null, searchQuery = "") }
    }


    private fun updateUiState(update: SearchScreenState.() -> SearchScreenState) {
        _uiState.update { it.update() }
    }


    private fun <T> updateList(newItems: List<T>, existingItems: List<T>?): List<T> {
        return existingItems?.toMutableList()?.apply { addAll(newItems) } ?: newItems
    }
}


// Olay sınıflarını tanımlayın
sealed class SearchScreenEvent {
    data object LoadSearchScreen : SearchScreenEvent()
    data object GetMovieNowPlayingList : SearchScreenEvent()
    data object GetShowOnAirList : SearchScreenEvent()
    data object SetStateEmpty : SearchScreenEvent()
    data class Search(val query: String) : SearchScreenEvent()

}

data class SearchScreenState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val searchResult: List<CardViewData?>? = null,
    val showOnAirList: List<CardViewData?>? = null,
    val movieNowPlayingList: List<CardViewData?>? = null
)
