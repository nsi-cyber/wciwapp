package com.nsicyber.wciwapp.data.remote.response.showGenreList


data class ShowGenreListResult(
    val page: Int?,
    val results: List<ShowGenreListItem?>?,
    val total_pages: Int?,
    val total_results: Int?
)