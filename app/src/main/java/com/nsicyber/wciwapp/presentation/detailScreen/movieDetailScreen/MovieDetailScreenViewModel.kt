package com.nsicyber.wciwapp.presentation.detailScreen.movieDetailScreen

import android.text.Html
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsicyber.wciwapp.common.ApiResult
import com.nsicyber.wciwapp.data.mapFunctions.parseParentalData
import com.nsicyber.wciwapp.data.remote.response.creditsList.CreditsListResponse
import com.nsicyber.wciwapp.data.remote.response.imageList.ImageListResponseItem
import com.nsicyber.wciwapp.data.remote.response.movieDetail.MovieDetailResponse
import com.nsicyber.wciwapp.data.remote.response.popularMoviesList.toCardViewData
import com.nsicyber.wciwapp.data.remote.response.providersList.ProviderItemData
import com.nsicyber.wciwapp.data.remote.response.providersList.ProvidersListResponseData
import com.nsicyber.wciwapp.data.remote.response.videosList.VideosListResponseItem
import com.nsicyber.wciwapp.domain.model.CardViewData
import com.nsicyber.wciwapp.domain.model.ParentalGuideCategoryList
import com.nsicyber.wciwapp.domain.useCase.movieDetailUseCases.GetMovieCreditsUseCase
import com.nsicyber.wciwapp.domain.useCase.movieDetailUseCases.GetMovieDetailUseCase
import com.nsicyber.wciwapp.domain.useCase.movieDetailUseCases.GetMovieImagesUseCase
import com.nsicyber.wciwapp.domain.useCase.movieDetailUseCases.GetMovieProvidersUseCase
import com.nsicyber.wciwapp.domain.useCase.movieDetailUseCases.GetMovieSimilarUseCase
import com.nsicyber.wciwapp.domain.useCase.movieDetailUseCases.GetMovieVideosUseCase
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
import kotlin.reflect.full.memberProperties

@HiltViewModel
class MovieDetailScreenViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieVideosUseCase: GetMovieVideosUseCase,
    private val getMovieSimilarUseCase: GetMovieSimilarUseCase,
    private val getMovieProvidersUseCase: GetMovieProvidersUseCase,
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase,
    private val getMovieImagesUseCase: GetMovieImagesUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailState())
    val uiState: StateFlow<MovieDetailState> = _uiState.asStateFlow()

    fun onEvent(event: MovieDetailEvent) {
        when (event) {
            is MovieDetailEvent.LoadMovieDetailScreen -> loadMovieDetailScreen(event.movieId)
        }
    }

    private fun loadMovieDetailScreen(movieId: Int?) = viewModelScope.launch {
        val detailFlow = getMovieDetailUseCase(movieId).map { result ->
            when (result) {
                is ApiResult.Success -> result.data
                is ApiResult.Error -> null
            }
        }
        val videosFlow = getMovieVideosUseCase(movieId).map { result ->
            when (result) {
                is ApiResult.Success -> result.data?.results
                is ApiResult.Error -> emptyList()
            }
        }
        val similarFlow = getMovieSimilarUseCase(movieId).map { result ->
            when (result) {
                is ApiResult.Success -> result.data?.results?.map { it?.toCardViewData() }
                is ApiResult.Error -> emptyList()
            }
        }
        val providersFlow = getMovieProvidersUseCase(movieId).map { result ->
            when (result) {
                is ApiResult.Success -> result.data?.results
                is ApiResult.Error -> null
            }
        }
        val creditsFlow = getMovieCreditsUseCase(movieId).map { result ->
            when (result) {
                is ApiResult.Success -> result.data
                is ApiResult.Error -> null
            }
        }
        val imagesFlow = getMovieImagesUseCase(movieId).map { result ->
            when (result) {
                is ApiResult.Success -> result.data?.backdrops?.sortedByDescending { it?.vote_count }
                is ApiResult.Error -> emptyList()
            }
        }

        combine(
            detailFlow,
            videosFlow,
            similarFlow,
            providersFlow,
            creditsFlow,
            imagesFlow
        ) { results ->
            val details = results[0] as MovieDetailResponse?
            val videos = results[1] as List<VideosListResponseItem?>?
            val similars = results[2] as List<CardViewData?>?
            val watchProviders = results[3] as ProvidersListResponseData?
            val credits = results[4] as CreditsListResponse?
            val images = results[5] as List<ImageListResponseItem?>?

            MovieDetailState(
                isLoading = false,
                details = details,
                videos = videos,
                similars = similars,
                watchProviders = watchProviders,
                credits = credits,
                images = images
            )
        }
            .onStart { updateUiState { copy(isLoading = true) } }
            .collect { newState ->
                _uiState.value = newState
                getParentalGuide(_uiState.value.details?.imdb_id)
            }
    }


    private fun getParentalGuide(imdbId: String?) {
        viewModelScope.launch {
            flow {
                val document = withContext(Dispatchers.IO) {
                    Jsoup.connect("https://www.imdb.com/title/$imdbId/parentalguide/")
                        .timeout(5000)
                        //.proxy("85.221.249.213", 8080)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
                        .get()
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


    private fun updateUiState(update: MovieDetailState.() -> MovieDetailState) {
        _uiState.update { it.update() }
    }
}

sealed class MovieDetailEvent {
    data class LoadMovieDetailScreen(val movieId: Int?) : MovieDetailEvent()
}


data class MovieDetailState(
    val isLoading: Boolean = false,
    val details: MovieDetailResponse? = null,
    val parentalGuide: ParentalGuideCategoryList? = null,
    val videos: List<VideosListResponseItem?>? = null,
    val credits: CreditsListResponse? = null,
    val images: List<ImageListResponseItem?>? = null,
    val watchProviders: ProvidersListResponseData? = null,
    val similars: List<CardViewData?>? = null
)

fun ProvidersListResponseData.getProviderByCountryCode(countryCode: String): ProviderItemData? {
    val property = ProvidersListResponseData::class.memberProperties
        .find { it.name.equals(countryCode, ignoreCase = true) }
    return property?.get(this) as? ProviderItemData
}

fun String?.fromHtmlString(): String {
    return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
}