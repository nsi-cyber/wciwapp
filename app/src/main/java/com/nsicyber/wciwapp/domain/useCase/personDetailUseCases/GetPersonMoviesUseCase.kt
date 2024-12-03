package com.nsicyber.wciwapp.domain.useCase.personDetailUseCases

import com.nsicyber.wciwapp.common.ApiResult
import com.nsicyber.wciwapp.data.remote.response.personMovieCreditList.PersonMovieCreditListResponse
import com.nsicyber.wciwapp.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPersonMoviesUseCase @Inject
constructor(
    private val networkRepository: NetworkRepository
) {
    operator fun invoke(
        personId: Int
    ): Flow<ApiResult<PersonMovieCreditListResponse?>> =
        flow {
            try {
                networkRepository.getPersonMovies(
                    personId
                )
                    .collect { result1 ->
                        emit(result1)
                    }
            } catch (e: Exception) {
                emit(ApiResult.Error(message = e.message.toString()))
            }

        }
}