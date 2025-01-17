package com.nsicyber.wciwapp.domain.useCase

import com.nsicyber.wciwapp.common.ApiResult
import com.nsicyber.wciwapp.data.remote.response.genreKeywordList.GenreKeywordListResponse
import com.nsicyber.wciwapp.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetMovieGenreListUseCase @Inject
constructor(
    private val networkRepository: NetworkRepository
) {
    operator fun invoke(): Flow<ApiResult<GenreKeywordListResponse?>> = flow {
        try {
            networkRepository.getMovieGenreList()
                .collect { result1 ->
                    emit(result1)
                }
        } catch (e: Exception) {
            emit(ApiResult.Error(message = e.message.toString()))
        }
    }
}


