package com.nsicyber.wciwapp.presentation.detailScreen.showDetailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsicyber.wciwapp.common.ApiResult
import com.nsicyber.wciwapp.data.mapFunctions.parseParentalData
import com.nsicyber.wciwapp.data.mapFunctions.toCardViewData
import com.nsicyber.wciwapp.data.remote.response.creditsList.CreditsListResponse
import com.nsicyber.wciwapp.data.remote.response.imageList.ImageListResponseItem
import com.nsicyber.wciwapp.data.remote.response.providersList.ProvidersListResponseData
import com.nsicyber.wciwapp.data.remote.response.showDetail.ShowDetailResponse
import com.nsicyber.wciwapp.data.remote.response.showSeasonDetail.toSeasonModel
import com.nsicyber.wciwapp.domain.model.CardViewData
import com.nsicyber.wciwapp.domain.useCase.showDetailUseCases.GetShowCreditsUseCase
import com.nsicyber.wciwapp.domain.useCase.showDetailUseCases.GetShowDetailUseCase
import com.nsicyber.wciwapp.domain.useCase.showDetailUseCases.GetShowExternalIdUseCase
import com.nsicyber.wciwapp.domain.useCase.showDetailUseCases.GetShowImagesUseCase
import com.nsicyber.wciwapp.domain.useCase.showDetailUseCases.GetShowProvidersUseCase
import com.nsicyber.wciwapp.domain.useCase.showDetailUseCases.GetShowSeasonDetailUseCase
import com.nsicyber.wciwapp.domain.useCase.showDetailUseCases.GetShowSimilarUseCase
import com.nsicyber.wciwapp.prese.ShowDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import javax.inject.Inject


@HiltViewModel
class ShowDetailScreenViewModel @Inject constructor(
    private val getShowDetailUseCase: GetShowDetailUseCase,
    private val getShowSimilarUseCase: GetShowSimilarUseCase,
    private val getShowProvidersUseCase: GetShowProvidersUseCase,
    private val getShowCreditsUseCase: GetShowCreditsUseCase,
    private val getShowImagesUseCase: GetShowImagesUseCase,
    private val getShowExternalIdUseCase: GetShowExternalIdUseCase,
    private val getShowSeasonDetailUseCase: GetShowSeasonDetailUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShowDetailState())
    val uiState: StateFlow<ShowDetailState> = _uiState.asStateFlow()

    fun onEvent(event: ShowDetailEvent) {
        when (event) {
            is ShowDetailEvent.LoadShowDetailScreen -> loadShowDetailScreen(event.showId)
            is ShowDetailEvent.GetShowSeasonDetail -> getShowSeasonDetail(
                _uiState.value.details?.id,
                event.seasonCount
            )
        }
    }

    private fun loadShowDetailScreen(showId: Int? = 0) = viewModelScope.launch {
        val detailFlow = getShowDetailUseCase(showId ?: 0).map { result ->
            when (result) {
                is ApiResult.Success -> result.data
                is ApiResult.Error -> null
            }
        }

        val similarFlow = getShowSimilarUseCase(showId).map { result ->
            when (result) {
                is ApiResult.Success -> result.data?.results?.map { it.toCardViewData() }
                is ApiResult.Error -> emptyList()
            }
        }
        val providersFlow = getShowProvidersUseCase(showId).map { result ->
            when (result) {
                is ApiResult.Success -> result.data?.results
                is ApiResult.Error -> null
            }
        }
        val creditsFlow = getShowCreditsUseCase(showId).map { result ->
            when (result) {
                is ApiResult.Success -> result.data
                is ApiResult.Error -> null
            }
        }
        val imagesFlow = getShowImagesUseCase(showId).map { result ->
            when (result) {
                is ApiResult.Success -> result.data?.backdrops?.sortedByDescending { it?.vote_count }
                is ApiResult.Error -> emptyList()
            }
        }

        combine(
            detailFlow,
            similarFlow,
            providersFlow,
            creditsFlow,
            imagesFlow
        ) { results ->
            val details = results[0] as ShowDetailResponse?
            val similars = results[1] as List<CardViewData?>?
            val watchProviders = results[2] as ProvidersListResponseData?
            val credits = results[3] as CreditsListResponse?
            val images = results[4] as List<ImageListResponseItem?>?

            ShowDetailState(
                isLoading = false,
                details = details,
                similars = similars,
                watchProviders = watchProviders,
                credits = credits,
                images = images
            )
        }
            .onStart { updateUiState { copy(isLoading = true) } }
            .collect { newState ->
                _uiState.value = newState
                getShowExternalId(_uiState.value.details?.id)
                getShowSeasonDetail(_uiState.value.details?.id, 1)
            }
    }


    private fun getShowExternalId(showId: Int?) {
        viewModelScope.launch {
            getShowExternalIdUseCase(showId).onStart {
                updateUiState { copy(isLoading = true) }
            }.onEach { result ->
                updateUiState { copy(isLoading = false) }
                when (result) {
                    is ApiResult.Success -> {
                        getParentalGuide(result.data?.imdb_id)
                    }

                    is ApiResult.Error -> {
                        // Hata durumunu burada ele alabilirsiniz
                    }
                }
            }.launchIn(this)
        }
    }

    private fun getShowSeasonDetail(showId: Int?, seasonCount: Int?) {
        viewModelScope.launch {
            getShowSeasonDetailUseCase(showId, seasonCount).onStart {
                updateUiState { copy(isLoading = true) }
            }.onEach { result ->
                when (result) {
                    is ApiResult.Success -> {

                        val updatedSeasonList =
                            (_uiState.value.seasonList?.toMutableList() ?: mutableListOf()).apply {
                                add(
                                    result.data.toSeasonModel().copy(
                                        seasonNumber = seasonCount
                                    )
                                )
                            }

                        updateUiState { copy(seasonList = updatedSeasonList) }
                    }

                    is ApiResult.Error -> {

                    }
                }
                updateUiState { copy(isLoading = false) }

            }.launchIn(this)
        }
    }

    private fun getParentalGuide(imdbId: String?) {
        viewModelScope.launch {
            flow {
                val document = withContext(Dispatchers.IO) {
                    Jsoup.connect("https://www.imdb.com/title/$imdbId/parentalguide/").get()
                }
                emit(document)
            }.onStart {
                updateUiState { copy(isLoading = true) }
            }.onEach { document ->
                val jsonData =
                    document.select("script[type=application/json]").firstOrNull()?.data()

                jsonData?.let {
                    val parentalGuideData = parseParentalData(it)
                    parentalGuideData.let { data ->
                        updateUiState { copy(parentalGuide = data) }
                    }
                }
            }.catch { e ->
                e.printStackTrace()
                updateUiState { copy(isLoading = false) }
            }.onCompletion {
                updateUiState { copy(isLoading = false) }
            }.launchIn(this)
        }
    }


    private fun updateUiState(update: ShowDetailState.() -> ShowDetailState) {
        _uiState.update { it.update() }
    }
}

