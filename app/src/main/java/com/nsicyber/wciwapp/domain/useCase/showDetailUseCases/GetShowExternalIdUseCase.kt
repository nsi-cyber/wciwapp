package com.nsicyber.wciwapp.domain.useCase.showDetailUseCases

import com.nsicyber.wciwapp.common.ApiResult
import com.nsicyber.wciwapp.data.remote.response.showExternalId.ExternalIdResponse
import com.nsicyber.wciwapp.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetShowExternalIdUseCase @Inject
constructor(
    private val networkRepository: NetworkRepository
) {
    operator fun invoke(showId: Int?): Flow<ApiResult<ExternalIdResponse?>> = flow {
        try {
            networkRepository.getShowExternalId(showId = showId)
                .collect { result1 ->
                    emit(result1)
                }
        } catch (e: Exception) {
            emit(ApiResult.Error(message = e.message.toString()))
        }
    }
}

