package com.nsicyber.wciwapp.data.remote.response.popularMoviesList


data class PopularMoviesListResponse(
    val page: Int?,
    val results: List<PopularMovieItem?>?,
    val total_pages: Int?,
    val total_results: Int?
)