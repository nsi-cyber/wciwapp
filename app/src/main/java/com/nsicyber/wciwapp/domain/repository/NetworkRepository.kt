package com.nsicyber.wciwapp.domain.repository

import com.nsicyber.wciwapp.common.ApiResult
import com.nsicyber.wciwapp.data.remote.response.creditsList.CreditsListResponse
import com.nsicyber.wciwapp.data.remote.response.genreKeywordList.GenreKeywordListResponse
import com.nsicyber.wciwapp.data.remote.response.imageList.ImageListResponse
import com.nsicyber.wciwapp.data.remote.response.movieDetail.MovieDetailResponse
import com.nsicyber.wciwapp.data.remote.response.movieGenreList.MovieGenreListResponse
import com.nsicyber.wciwapp.data.remote.response.movieNowPlayingList.MovieNowPlayingListResponse
import com.nsicyber.wciwapp.data.remote.response.popularMoviesList.PopularMoviesListResponse
import com.nsicyber.wciwapp.data.remote.response.providersList.ProvidersListResponse
import com.nsicyber.wciwapp.data.remote.response.searchResultList.SearchResultListResponse
import com.nsicyber.wciwapp.data.remote.response.showDetail.ShowDetailResponse
import com.nsicyber.wciwapp.data.remote.response.showExternalId.ExternalIdResponse
import com.nsicyber.wciwapp.data.remote.response.showGenreList.ShowGenreListResult
import com.nsicyber.wciwapp.data.remote.response.showOnAirList.ShowOnAirListResponse
import com.nsicyber.wciwapp.data.remote.response.showSeasonDetail.ShowSeasonDetailResponse
import com.nsicyber.wciwapp.data.remote.response.showSimilarList.ShowSimilarListResponse
import com.nsicyber.wciwapp.data.remote.response.topRatedShowsList.TopRatedShowsListResponse
import com.nsicyber.wciwapp.data.remote.response.trendingList.TrendingListResponse
import com.nsicyber.wciwapp.data.remote.response.videosList.VideosListResponse
import kotlinx.coroutines.flow.Flow


interface NetworkRepository {

    suspend fun getMovieDetail(movieId: Int?): Flow<ApiResult<MovieDetailResponse?>>
    suspend fun getMovieCredits(movieId: Int?): Flow<ApiResult<CreditsListResponse?>>
    suspend fun getMovieImages(movieId: Int?): Flow<ApiResult<ImageListResponse?>>
    suspend fun getMovieSimilar(movieId: Int?): Flow<ApiResult<PopularMoviesListResponse?>>
    suspend fun getMovieVideos(movieId: Int?): Flow<ApiResult<VideosListResponse?>>
    suspend fun getMovieProviders(movieId: Int?): Flow<ApiResult<ProvidersListResponse?>>


    suspend fun getShowCredits(showId: Int?): Flow<ApiResult<CreditsListResponse?>>
    suspend fun getShowDetail(showId: Int): Flow<ApiResult<ShowDetailResponse?>>
    suspend fun getShowProviders(showId: Int?): Flow<ApiResult<ProvidersListResponse?>>
    suspend fun getShowImages(showId: Int?): Flow<ApiResult<ImageListResponse?>>
    suspend fun getShowSimilar(showId: Int?): Flow<ApiResult<ShowSimilarListResponse?>>
    suspend fun getShowExternalId(showId: Int?): Flow<ApiResult<ExternalIdResponse?>>
    suspend fun getShowSeasonDetail(
        showId: Int?,
        seasonNumber: Int?
    ): Flow<ApiResult<ShowSeasonDetailResponse?>>


    suspend fun getPopularMovies(page: Int): Flow<ApiResult<PopularMoviesListResponse?>>

    suspend fun getSearchResults(
        query: String,
        page: Int
    ): Flow<ApiResult<SearchResultListResponse?>>


    suspend fun getTopRatedShows(page: Int): Flow<ApiResult<TopRatedShowsListResponse?>>
    suspend fun getTrending(): Flow<ApiResult<TrendingListResponse?>>


    suspend fun getTopMovies(page: Int): Flow<ApiResult<PopularMoviesListResponse?>>
    suspend fun getTvGenreList(): Flow<ApiResult<GenreKeywordListResponse?>>
    suspend fun getMovieGenreList(): Flow<ApiResult<GenreKeywordListResponse?>>

    suspend fun getMovieGenreDetailList(
        page: Int,
        genreId: Int
    ): Flow<ApiResult<MovieGenreListResponse?>>

    suspend fun getShowGenreDetailList(
        page: Int,
        genreId: Int
    ): Flow<ApiResult<ShowGenreListResult?>>



    suspend fun getMovieNowPlayingList(
        page: Int,
    ): Flow<ApiResult<MovieNowPlayingListResponse?>>


    suspend fun getShowOnAirList(
        page: Int,
    ): Flow<ApiResult<ShowOnAirListResponse?>>


}


