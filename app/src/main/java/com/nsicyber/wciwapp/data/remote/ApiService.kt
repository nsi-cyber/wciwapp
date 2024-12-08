package com.nsicyber.wciwapp.data.remote

import com.nsicyber.wciwapp.common.Constants
import com.nsicyber.wciwapp.data.remote.response.creditsList.CreditsListResponse
import com.nsicyber.wciwapp.data.remote.response.genreKeywordList.GenreKeywordListResponse
import com.nsicyber.wciwapp.data.remote.response.imageList.ImageListResponse
import com.nsicyber.wciwapp.data.remote.response.movieDetail.MovieDetailResponse
import com.nsicyber.wciwapp.data.remote.response.movieGenreList.MovieGenreListResponse
import com.nsicyber.wciwapp.data.remote.response.movieNowPlayingList.MovieNowPlayingListResponse
import com.nsicyber.wciwapp.data.remote.response.personDetail.PersonDetailResponse
import com.nsicyber.wciwapp.data.remote.response.personImageList.PersonImageListResponse
import com.nsicyber.wciwapp.data.remote.response.personMovieCreditList.PersonMovieCreditListResponse
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
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Movies
    @GET(Constants.Endpoints.MOVIE_DETAIL)
    suspend fun getMovieDetail(
        @Path("movieId") movieId: Int?,
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<MovieDetailResponse?>

    @GET(Constants.Endpoints.MOVIE_CREDITS)
    suspend fun getMovieCredits(
        @Path("movieId") movieId: Int?,
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<CreditsListResponse?>

    @GET(Constants.Endpoints.MOVIE_IMAGES)
    suspend fun getMovieImages(
        @Path("movieId") movieId: Int?
    ): Response<ImageListResponse?>

    @GET(Constants.Endpoints.MOVIE_SIMILAR)
    suspend fun getMovieSimilar(
        @Path("movieId") movieId: Int?,
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<PopularMoviesListResponse?>

    @GET(Constants.Endpoints.MOVIE_VIDEOS)
    suspend fun getMovieVideos(
        @Path("movieId") movieId: Int?,
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<VideosListResponse?>

    @GET(Constants.Endpoints.MOVIE_PROVIDERS)
    suspend fun getMovieProviders(
        @Path("movieId") movieId: Int?
    ): Response<ProvidersListResponse?>

    @GET(Constants.Endpoints.MOVIE_POPULAR)
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<PopularMoviesListResponse?>

    @GET(Constants.Endpoints.SEARCH_MULTI)
    suspend fun getSearchResults(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("language") language: String = Constants.BASE_LANGUAGE

    ): Response<SearchResultListResponse?>

    // Shows
    @GET(Constants.Endpoints.SHOW_DETAIL)
    suspend fun getShowDetail(
        @Path("showId") showId: Int,
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<ShowDetailResponse?>

    @GET(Constants.Endpoints.TOP_RATED_SHOWS)
    suspend fun getTopRatedShows(
        @Query("page") page: Int,
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<TopRatedShowsListResponse?>

    @GET(Constants.Endpoints.TRENDING_ALL_WEEK)
    suspend fun getTrendingAll(
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<TrendingListResponse?>

    @GET(Constants.Endpoints.DISCOVER_MOVIE)
    suspend fun getMovieGenre(
        @Query("page") page: Int,
        @Query("with_genres") genreId: Int,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<MovieGenreListResponse?>

    @GET(Constants.Endpoints.DISCOVER_TV)
    suspend fun getTvSeriesGenre(
        @Query("page") page: Int,
        @Query("with_genres") genreId: Int,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<ShowGenreListResult?>

    @GET(Constants.Endpoints.MOVIE_TOP_RATED)
    suspend fun getTopMovies(
        @Query("page") page: Int,
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<PopularMoviesListResponse?>

    @GET(Constants.Endpoints.GENRE_MOVIE_LIST)
    suspend fun getMovieGenreList(
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<GenreKeywordListResponse?>

    @GET(Constants.Endpoints.GENRE_TV_LIST)
    suspend fun getTvGenreList(
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<GenreKeywordListResponse?>


    @GET(Constants.Endpoints.SHOW_EXTERNAL_ID)
    suspend fun getShowExternalId(
        @Path("showId") showId: Int?,
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<ExternalIdResponse?>


    @GET(Constants.Endpoints.SHOW_PROVIDERS)
    suspend fun getShowProviders(
        @Path("showId") showId: Int?
    ): Response<ProvidersListResponse?>


    @GET(Constants.Endpoints.SHOW_SIMILAR)
    suspend fun getShowSimilar(
        @Path("showId") showId: Int?
    ): Response<ShowSimilarListResponse?>


    @GET(Constants.Endpoints.SHOW_IMAGES)
    suspend fun getShowImages(
        @Path("showId") showId: Int?
    ): Response<ImageListResponse?>


    @GET(Constants.Endpoints.SHOW_CREDITS)
    suspend fun getShowCredits(
        @Path("showId") showId: Int?,
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<CreditsListResponse?>

    @GET(Constants.Endpoints.SHOW_SEASON)
    suspend fun getShowSeasonDetail(
        @Path("showId") showId: Int?,
        @Path("seasonNumber") seasonNumber: Int?,
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<ShowSeasonDetailResponse?>


    @GET(Constants.Endpoints.SHOW_VIDEOS)
    suspend fun getShowVideos(
        @Path("showId") showId: Int?,
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<VideosListResponse?>


    @GET(Constants.Endpoints.SHOW_ON_AIR)
    suspend fun getShowOnAirList(
        @Query("page") page: Int,
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<ShowOnAirListResponse?>

    @GET(Constants.Endpoints.MOVIE_NOW_PLAYING)
    suspend fun getMovieNowPlayingList(
        @Query("page") page: Int,
        @Query("language") language: String = Constants.BASE_LANGUAGE,
        @Query("region") region: String = Constants.BASE_COUNTRY
    ): Response<MovieNowPlayingListResponse?>


    @GET(Constants.Endpoints.PERSON_MOVIE_CREDITS)
    suspend fun getPersonMovies(
        @Path("person_id") personId: Int,
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<PersonMovieCreditListResponse?>

    @GET(Constants.Endpoints.PERSON_SHOW_CREDITS)
    suspend fun getPersonShows(
        @Path("person_id") personId: Int,
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<PersonMovieCreditListResponse?>


    @GET(Constants.Endpoints.PERSON_IMAGES)
    suspend fun getPersonImages(
        @Path("person_id") personId: Int?
    ): Response<PersonImageListResponse?>



    @GET(Constants.Endpoints.PERSON_DETAIL)
    suspend fun getPersonDetail(
        @Path("person_id") personId: Int?,
        @Query("language") language: String = Constants.BASE_LANGUAGE
    ): Response<PersonDetailResponse?>



}