package com.nsicyber.wciwapp.presentation.exploreScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nsicyber.wciwapp.presentation.components.BaseView
import com.nsicyber.wciwapp.presentation.components.PaginationListContent
import com.nsicyber.wciwapp.presentation.components.RankedListContent
import com.nsicyber.wciwapp.presentation.components.TrendingSection
import com.nsicyber.wciwapp.primaryColor

@Composable
fun ExploreScreen(
    exploreScreenViewModel: ExploreScreenViewModel = hiltViewModel<ExploreScreenViewModel>(),
    onMovieDetailClicked: (Int) -> Unit,
    onShowDetailClicked: (Int) -> Unit,
    onSearchClicked: () -> Unit,
) {

    val uiState by exploreScreenViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        exploreScreenViewModel.onEvent(ExploreScreenEvent.LoadExploreScreen)
    }

    val lazyState = rememberLazyListState()

    LaunchedEffect(uiState.trendingAll) {
        if (!uiState.trendingAll.isNullOrEmpty()) lazyState.animateScrollToItem(0)
    }

    BaseView(isPageLoading = uiState.isLoading, content = {
        LazyColumn(
            state = lazyState,
            modifier = Modifier
                .background(primaryColor),contentPadding = PaddingValues(bottom = 32.dp)
        ) {

            item {
                uiState.trendingAll?.takeIf { it.isNotEmpty() }?.let { trending ->
                    TrendingSection(
                        trending,
                        onSearchClicked = { onSearchClicked() },
                        onMovieDetailClicked = { onMovieDetailClicked(it ?: 0) },
                        onShowDetailClicked = { onShowDetailClicked(it ?: 0) },
                    )
                }
            }

            item {
                uiState.popularMovies?.takeIf { it.isNotEmpty() }?.let { popularMovies ->
                    PaginationListContent(
                        list = popularMovies,
                        onItemClick = { id -> onMovieDetailClicked(id) },
                        pagination = { exploreScreenViewModel.onEvent(ExploreScreenEvent.LoadPopularMovies) }
                    )


                }
            }

            item {
                uiState.topRatedShows?.takeIf { it.isNotEmpty() }?.let { topRatedShows ->
                    RankedListContent(
                        title = "Top 10 Shows",
                        subtitle = "This Week",
                        list = topRatedShows,
                        onItemClick = { id -> onShowDetailClicked(id) }
                    )
                }
            }


            item {
                uiState.topRatedMovies?.takeIf { it.isNotEmpty() }?.let { topRatedMovies ->
                    RankedListContent(
                        title = "Top 10 Movies",
                        subtitle = "of All Time",
                        list = topRatedMovies,
                        onItemClick = { id -> onMovieDetailClicked(id) }
                    )
                }
            }


        }
    })

}

