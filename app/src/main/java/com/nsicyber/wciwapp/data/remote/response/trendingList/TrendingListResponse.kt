package com.nsicyber.wciwapp.data.remote.response.trendingList


data class TrendingListResponse(
    val page: Int?,
    val results: List<TrendingListItem>,
    val total_pages: Int?,
    val total_results: Int?
)