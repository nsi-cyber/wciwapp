package com.nsicyber.wciwapp.domain.useCase.movieDetailUseCases

import com.nsicyber.wciwapp.common.ApiResult
import com.nsicyber.wciwapp.data.remote.response.providersList.ProvidersListResponse
import com.nsicyber.wciwapp.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetMovieProvidersUseCase @Inject
constructor(
    private val networkRepository: NetworkRepository
) {
    operator fun invoke(movieId: Int?): Flow<ApiResult<ProvidersListResponse?>> =
        flow {
            try {
                networkRepository.getMovieProviders(movieId)
                    .collect { result1 ->
                        emit(result1)
                    }
            } catch (e: Exception) {
                emit(ApiResult.Error(message = e.message.toString()))
            }
        }
}
