package com.nsicyber.wciwapp.data.remote.response.searchResultList


data class SearchResultListResponse(
    val page: Int,
    val results: List<SearchResultItem>,
    val total_pages: Int,
    val total_results: Int
)