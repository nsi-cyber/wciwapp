package com.nsicyber.wciwapp.data.remote.response.showOnAirList

data class ShowOnAirListResponse(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)