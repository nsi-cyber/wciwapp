package com.nsicyber.wciwapp.domain.useCase.showDetailUseCases

import com.nsicyber.wciwapp.common.ApiResult
import com.nsicyber.wciwapp.data.remote.response.showSeasonDetail.ShowSeasonDetailResponse
import com.nsicyber.wciwapp.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetShowSeasonDetailUseCase @Inject
constructor(
    private val networkRepository: NetworkRepository
) {
    operator fun invoke(
        showId: Int?,
        seasonNumber: Int?
    ): Flow<ApiResult<ShowSeasonDetailResponse?>> =
        flow {
            try {
                networkRepository.getShowSeasonDetail(showId = showId, seasonNumber = seasonNumber)
                    .collect { result1 ->
                        emit(result1)
                    }
            } catch (e: Exception) {
                emit(ApiResult.Error(message = e.message.toString()))
            }

        }
}