package com.nsicyber.wciwapp.domain.useCase

import com.nsicyber.wciwapp.common.ApiResult
import com.nsicyber.wciwapp.data.remote.response.movieNowPlayingList.MovieNowPlayingListResponse
import com.nsicyber.wciwapp.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetMovieNowPlayingListUseCase @Inject
constructor(
    private val networkRepository: NetworkRepository
) {
    operator fun invoke(page:Int): Flow<ApiResult<MovieNowPlayingListResponse?>> = flow {
        try {
            networkRepository.getMovieNowPlayingList(page)
                .collect { result1 ->
                    emit(result1)
                }
        } catch (e: Exception) {
            emit(ApiResult.Error(message = e.message.toString()))
        }
    }
}


