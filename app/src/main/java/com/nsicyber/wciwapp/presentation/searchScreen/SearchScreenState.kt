package com.nsicyber.wciwapp.presentation.searchScreen

import com.nsicyber.wciwapp.domain.model.CardViewData


data class SearchScreenState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val searchResult: List<CardViewData?>? = null,
    val showOnAirList: List<CardViewData?>? = null,
    val movieNowPlayingList: List<CardViewData?>? = null
)
