package com.nsicyber.wciwapp.data.repository

import com.nsicyber.wciwapp.common.ApiResult
import com.nsicyber.wciwapp.common.apiFlow
import com.nsicyber.wciwapp.data.remote.ApiService
import com.nsicyber.wciwapp.data.remote.response.creditsList.CreditsListResponse
import com.nsicyber.wciwapp.data.remote.response.imageList.ImageListResponse
import com.nsicyber.wciwapp.data.remote.response.personDetail.PersonDetailResponse
import com.nsicyber.wciwapp.data.remote.response.personImageList.PersonImageListResponse
import com.nsicyber.wciwapp.data.remote.response.personMovieCreditList.PersonMovieCreditListResponse
import com.nsicyber.wciwapp.data.remote.response.providersList.ProvidersListResponse
import com.nsicyber.wciwapp.data.remote.response.showExternalId.ExternalIdResponse
import com.nsicyber.wciwapp.data.remote.response.showSeasonDetail.ShowSeasonDetailResponse
import com.nsicyber.wciwapp.data.remote.response.showSimilarList.ShowSimilarListResponse
import com.nsicyber.wciwapp.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class NetworkRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    NetworkRepository {

    override suspend fun getMovieDetail(movieId: Int?) = apiFlow {
        apiService.getMovieDetail(movieId)
    }

    override suspend fun getMovieCredits(movieId: Int?) = apiFlow {
        apiService.getMovieCredits(movieId)
    }

    override suspend fun getMovieImages(movieId: Int?) = apiFlow {
        apiService.getMovieImages(movieId)
    }

    override suspend fun getMovieSimilar(movieId: Int?) = apiFlow {
        apiService.getMovieSimilar(movieId)
    }

    override suspend fun getMovieVideos(movieId: Int?) = apiFlow {
        apiService.getMovieVideos(movieId)
    }

    override suspend fun getMovieProviders(movieId: Int?) = apiFlow {
        apiService.getMovieProviders(movieId)
    }

    override suspend fun getShowCredits(showId: Int?): Flow<ApiResult<CreditsListResponse?>> =
        apiFlow {
            apiService.getShowCredits(showId)
        }


    override suspend fun getPopularMovies(page: Int) = apiFlow {
        apiService.getPopularMovies(page)

    }

    override suspend fun getSearchResults(query: String, page: Int) = apiFlow {
        apiService.getSearchResults(query, page)

    }

    override suspend fun getShowDetail(showId: Int) = apiFlow {
        apiService.getShowDetail(showId)
    }

    override suspend fun getShowProviders(showId: Int?): Flow<ApiResult<ProvidersListResponse?>> =
        apiFlow {
            apiService.getShowProviders(showId)
        }

    override suspend fun getShowImages(showId: Int?): Flow<ApiResult<ImageListResponse?>> =
        apiFlow {
            apiService.getShowImages(showId)
        }


    override suspend fun getShowSimilar(showId: Int?): Flow<ApiResult<ShowSimilarListResponse?>> =
        apiFlow {
            apiService.getShowSimilar(showId)
        }


    override suspend fun getShowExternalId(showId: Int?): Flow<ApiResult<ExternalIdResponse?>> =
        apiFlow {
            apiService.getShowExternalId(showId)
        }


    override suspend fun getShowSeasonDetail(
        showId: Int?,
        seasonNumber: Int?
    ): Flow<ApiResult<ShowSeasonDetailResponse?>> = apiFlow {
        apiService.getShowSeasonDetail(showId, seasonNumber)
    }

    override suspend fun getTopRatedShows(page: Int) = apiFlow {
        apiService.getTopRatedShows(page)
    }

    override suspend fun getTrending() = apiFlow {
        apiService.getTrendingAll()

    }

    override suspend fun getTopMovies(page: Int) = apiFlow {
        apiService.getTopMovies(page)

    }

    override suspend fun getTvGenreList() = apiFlow {
        apiService.getTvGenreList()

    }

    override suspend fun getMovieGenreList() = apiFlow {
        apiService.getMovieGenreList()

    }

    override suspend fun getMovieGenreDetailList(page: Int, genreId: Int) = apiFlow {
        apiService.getMovieGenre(page = page, genreId = genreId)

    }


    override suspend fun getShowGenreDetailList(page: Int, genreId: Int) = apiFlow {

        apiService.getTvSeriesGenre(page = page, genreId = genreId)

    }

    override suspend fun getMovieNowPlayingList(page: Int) = apiFlow {
        apiService.getMovieNowPlayingList(page = page)
    }

    override suspend fun getShowOnAirList(page: Int) = apiFlow {
        apiService.getShowOnAirList(page = page)

    }




    override suspend fun getPersonMovies(
        personId: Int
    ): Flow<ApiResult<PersonMovieCreditListResponse?>> = apiFlow {
        apiService.getPersonMovies( personId = personId)

    }

    override suspend fun getPersonShows(
        personId: Int
    ): Flow<ApiResult<PersonMovieCreditListResponse?>> = apiFlow {
        apiService.getPersonShows( personId = personId)

    }

    override suspend fun getPersonImages(personId: Int?): Flow<ApiResult<PersonImageListResponse?>> = apiFlow {
        apiService.getPersonImages(personId = personId)

    }
    override suspend fun getPersonDetail(personId: Int?): Flow<ApiResult<PersonDetailResponse?>>  = apiFlow {
        apiService.getPersonDetail(personId = personId)

    }

}
