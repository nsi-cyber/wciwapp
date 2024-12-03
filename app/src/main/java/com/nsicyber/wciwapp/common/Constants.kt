package com.nsicyber.wciwapp.common

import java.util.Locale


object Constants {


    val IMAGE_URL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/"
    val IMAGE_URL_6_6 = "https://image.tmdb.org/t/p/w600_and_h900_bestv2/"
    val IMAGE_URL_ORIGINAL = "https://image.tmdb.org/t/p/original/"
    val IMAGE_URL_PROFILE = "https://media.themoviedb.org/t/p/w276_and_h350_face/"
    val YOUTUBE_THUMB_URL = "https://i.ytimg.com/vi/${1}/hqdefault.jpg"
    val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v=${1}"
    val IMDB_BASE_URL = "https://api.themoviedb.org/3/"
    val BASE_LANGUAGE = Locale.getDefault().language
    val BASE_COUNTRY = Locale.getDefault().country


    object Endpoints {


        const val SEARCH_MULTI = "search/multi"
        const val TRENDING_ALL_WEEK = "trending/all/week"


        // Endpoints for Movies
        const val MOVIE_DETAIL = "movie/{movieId}"
        const val MOVIE_CREDITS = "movie/{movieId}/credits"
        const val MOVIE_IMAGES = "movie/{movieId}/images"
        const val MOVIE_SIMILAR = "movie/{movieId}/similar"
        const val MOVIE_VIDEOS = "movie/{movieId}/videos"
        const val MOVIE_PROVIDERS = "movie/{movieId}/watch/providers"
        const val DISCOVER_MOVIE = "discover/movie"
        const val MOVIE_POPULAR = "movie/popular"
        const val GENRE_MOVIE_LIST = "genre/movie/list"
        const val MOVIE_TOP_RATED = "movie/top_rated"

        // Endpoints for Shows
        const val SHOW_DETAIL = "tv/{showId}"
        const val SHOW_SEASON = "tv/{showId}/season/{seasonNumber}"
        const val SHOW_CREDITS = "tv/{showId}/credits"
        const val SHOW_IMAGES = "tv/{showId}/images"
        const val SHOW_SIMILAR = "tv/{showId}/similar"
        const val SHOW_PROVIDERS = "tv/{showId}/watch/providers"
        const val SHOW_EXTERNAL_ID = "tv/{showId}/external_ids"

        const val TOP_RATED_SHOWS = "tv/top_rated"
        const val DISCOVER_TV = "discover/tv"
        const val GENRE_TV_LIST = "genre/tv/list"


        const val SHOW_ON_AIR = "tv/on_the_air"
        const val MOVIE_NOW_PLAYING = "movie/now_playing"


        const val PERSON_IMAGES = "person/{person_id}/images"
        const val PERSON_SHOW_CREDITS = "person/{person_id}/tv_credits"
        const val PERSON_MOVIE_CREDITS = "person/{person_id}/movie_credits"
        const val PERSON_DETAIL = "person/{person_id}"



    }


    object Routes {
        val EXPLORE_SCREEN = "explore_screen"
        val SEARCH_SCREEN = "search_screen"
        val MOVIE_DETAIL_SCREEN = "movie_detail_screen/"
        val SHOW_DETAIL_SCREEN = "show_detail_screen/"
        val PERSON_DETAIL_SCREEN = "person_detail_screen/"
    }

}