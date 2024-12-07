package com.nsicyber.wciwapp.presentation.personScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nsicyber.wciwapp.common.ApiResult
import com.nsicyber.wciwapp.data.remote.response.personDetail.PersonDetailResponse
import com.nsicyber.wciwapp.data.remote.response.personImageList.PersonImageListResponse
import com.nsicyber.wciwapp.data.remote.response.personMovieCreditList.PersonMovieCreditListResponse
import com.nsicyber.wciwapp.domain.useCase.personDetailUseCases.GetPersonDetailUseCase
import com.nsicyber.wciwapp.domain.useCase.personDetailUseCases.GetPersonImagesUseCase
import com.nsicyber.wciwapp.domain.useCase.personDetailUseCases.GetPersonMoviesUseCase
import com.nsicyber.wciwapp.domain.useCase.personDetailUseCases.GetPersonShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailScreenViewModel @Inject
constructor(
    private val getPersonImagesUseCase: GetPersonImagesUseCase,
    private val getPersonDetailUseCase: GetPersonDetailUseCase,
    private val getPersonMoviesUseCase: GetPersonMoviesUseCase,
    private val getPersonShowsUseCase: GetPersonShowsUseCase,
) : ViewModel() {


    private val _uiState = MutableStateFlow(PersonDetailScreenState())
    val uiState: StateFlow<PersonDetailScreenState> = _uiState.asStateFlow()





    fun onEvent(event: PersonDetailScreenEvent) {
        when (event) {
            is PersonDetailScreenEvent.LoadPersonDetailScreen -> loadPersonDetailScreen(event.personId)

        }
    }

    private fun loadPersonDetailScreen(personId: Int) = viewModelScope.launch {

        val personMoviesFlow =
            getPersonMoviesUseCase(
                personId = personId
            ).map { result ->
                when (result) {
                    is ApiResult.Success -> result.data
                    is ApiResult.Error -> null
                }
            }

        val personShowsFlow =
            getPersonShowsUseCase(
                personId = personId
            ).map { result ->

                when (result) {
                    is ApiResult.Success -> result.data
                    is ApiResult.Error -> null
                }
            }
        val personImagesFlow = getPersonImagesUseCase(personId).map { result ->
            when (result) {
                is ApiResult.Success -> result.data
                is ApiResult.Error -> null
            }
        }

        val personDetailFlow = getPersonDetailUseCase(personId).map { result ->
            when (result) {
                is ApiResult.Success -> result.data
                is ApiResult.Error -> null
            }
        }

        combine(
            personMoviesFlow,
            personShowsFlow,
            personImagesFlow,
            personDetailFlow,

            ) { results ->
            val personMovies = results[0] as PersonMovieCreditListResponse?
            val personShows = results[1] as PersonMovieCreditListResponse?
            val personImages = results[2] as PersonImageListResponse?
            val personDetail = results[3] as PersonDetailResponse?

            PersonDetailScreenState(
                isLoading = false,
                movies = personMovies,
                shows = personShows,
                images = personImages?.profiles,
                personDetail = personDetail,

                )
        }
            .onStart { updateUiState { copy(isLoading = true) } }
            .collect { newState -> _uiState.value = newState }
    }



    private fun updateUiState(update: PersonDetailScreenState.() -> PersonDetailScreenState) {
        _uiState.update { it.update() }
    }

    private fun <T> updateList(newItems: List<T>, existingItems: List<T>?): List<T> {
        return existingItems?.toMutableList()?.apply { addAll(newItems) } ?: newItems
    }
}