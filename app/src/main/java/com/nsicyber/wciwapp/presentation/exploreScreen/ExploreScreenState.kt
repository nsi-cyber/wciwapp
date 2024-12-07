package com.nsicyber.wciwapp.presentation.exploreScreen

import com.nsicyber.wciwapp.domain.model.CardViewData

data class ExploreScreenState(
    val isLoading: Boolean = true,
    val popularMovies: List<CardViewData?>? = null,
    val topRatedShows: List<CardViewData?>? = null,
    val topRatedMovies: List<CardViewData?>? = null,
    val trendingAll: List<CardViewData?>? = null,
)
