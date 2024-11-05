package com.nsicyber.wciwapp.data.remote.response.topRatedShowsList


data class TopRatedShowsListResponse(
    val page: Int?,
    val results: List<TopRatedShowsItem?>?,
    val total_pages: Int?,
    val total_results: Int?
)