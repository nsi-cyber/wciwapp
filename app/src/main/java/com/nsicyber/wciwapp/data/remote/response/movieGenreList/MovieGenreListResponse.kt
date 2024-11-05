package com.nsicyber.wciwapp.data.remote.response.movieGenreList


data class MovieGenreListResponse(
    val page: Int?,
    val results: List<MovieGenreListItem?>?,
    val total_pages: Int?,
    val total_results: Int?
)