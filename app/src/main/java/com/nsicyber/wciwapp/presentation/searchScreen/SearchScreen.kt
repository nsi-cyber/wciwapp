package com.nsicyber.wciwapp.presentation.searchScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nsicyber.wciwapp.common.Constants
import com.nsicyber.wciwapp.data.remote.response.genreKeywordList.Genre
import com.nsicyber.wciwapp.domain.model.CardViewData
import com.nsicyber.wciwapp.presentation.components.BaseView
import com.nsicyber.wciwapp.presentation.components.PaginationListContent
import com.nsicyber.wciwapp.presentation.components.shimmerEffect
import com.nsicyber.wciwapp.primaryColor
import com.nsicyber.wciwapp.secondaryColor


@Composable
fun SearchScreen(
    searchScreenViewModel: SearchScreenViewModel = hiltViewModel(),
    onMovieDetailClicked: (movieId: Int?) -> Unit = {},
    onShowDetailClicked: (showId: Int?) -> Unit = {}
) {
    val uiState by searchScreenViewModel.uiState.collectAsState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        searchScreenViewModel.onEvent(SearchScreenEvent.LoadSearchScreen)
        focusRequester.requestFocus()
    }

    BaseView(isPageLoading = uiState.isLoading, content = {
        LazyColumn(
            modifier = Modifier
                .background(primaryColor)
                .fillMaxSize()
                .padding(top = 16.dp), contentPadding = PaddingValues(bottom = 32.dp)
        ) {

            item {
                SearchInputField(
                    focusRequester = focusRequester,
                    query = uiState.searchQuery,
                    onQueryChange = { value ->
                        searchScreenViewModel.onEvent(SearchScreenEvent.Search(value))
                    },
                    onClearQuery = {
                        searchScreenViewModel.onEvent(SearchScreenEvent.SetStateEmpty)
                    }
                )
            }


            if (uiState.searchResult != null) {
                if (uiState.searchResult.isNullOrEmpty()) {
                    item {
                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            NotFoundCard()
                        }
                    }
                } else {
                    items(uiState.searchResult ?: listOf()) { result ->
                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            SearchCard(data = result) {
                                if (result?.media_type == "movie") {
                                    onMovieDetailClicked(result.id)
                                } else if (result?.media_type == "tv") {
                                    onShowDetailClicked(result.id)
                                }

                            }
                        }

                    }
                }

            } else {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp, end = 16.dp)
                            .background(primaryColor)
                    ) {

                        uiState.movieNowPlayingList?.takeIf { it.isNotEmpty() }
                            ?.let { popularMovies ->
                                PaginationListContent(title = "Now Playing", subtitle = "Movies",
                                    list = popularMovies,
                                    onItemClick = { id -> onMovieDetailClicked(id) },
                                    pagination = { searchScreenViewModel.onEvent(SearchScreenEvent.GetMovieNowPlayingList) }
                                )


                            }
                        Spacer(modifier = Modifier.height(16.dp))

                        uiState.showOnAirList?.takeIf { it.isNotEmpty() }?.let { popularMovies ->
                            PaginationListContent(title = "On The Air", subtitle = "Shows",
                                list = popularMovies,
                                onItemClick = { id -> onShowDetailClicked(id) },
                                pagination = { searchScreenViewModel.onEvent(SearchScreenEvent.GetShowOnAirList) }
                            )


                        }
                    }
                }

            }


        }
    })

}


@Composable
fun SearchInputField(
    focusRequester: FocusRequester,
    query: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
            .padding(horizontal = 24.dp)
            .shadow(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                value = query,
                onValueChange = onQueryChange,
                maxLines = 1,
                placeholder = {
                    Text(
                        text = "Discover Movies & Shows...",
                        color = Color.Black,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                ),
                modifier = Modifier
                    .wrapContentSize()
                    .focusRequester(focusRequester)
                    .background(color = Color.White)
            )

            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onClearQuery() },
                imageVector = if(query.isNotEmpty()) Icons.Default.Clear else Icons.Default.Search,
                contentDescription = "Clear Icon",
                tint = Color.Black
            )
        }
    }
}


@Composable
fun SearchCard(
    data: CardViewData?, onItemClick: (data: CardViewData?) -> Unit = {}
) {

    Row(
        modifier = Modifier
            .height(100.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .clickable {
                onItemClick(data)
            }
            .padding(bottom = 16.dp)


    ) {


        AsyncImage(
            model = "${Constants.IMAGE_URL}${data?.poster_path}",
            contentDescription = data?.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(4 / 5f)
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .clip(
                    RoundedCornerShape(10.dp)
                )
                .background(Color.Gray)

        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = data?.title.orEmpty() + " (" + data?.date?.take(4) + ")",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = if (data?.media_type == "movie") "Movie" else "Tv Show",
                color = Color.Gray,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
            )
        }


    }

}


@Composable
fun NotFoundCard() {

    Row(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()

            .padding(bottom = 16.dp)


    ) {


        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "No result to show...",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Medium,
            )

        }


    }

}


@Composable
fun HorizontalKeywordContent(data: List<Genre?>?, onClick: (id: Int?) -> Unit) {
    LazyHorizontalStaggeredGrid(
        modifier = Modifier.height(86.dp),
        contentPadding = PaddingValues(start = 12.dp, end = 12.dp),
        rows = StaggeredGridCells
            .Fixed(2) // number of columns
    ) {
        if (data.isNullOrEmpty()) {
            items(9) {
                KeywordCardItem(modifier = Modifier.shimmerEffect())

            }
        }
        items(
            data?.size ?: 0
        ) { item ->

            KeywordCardItem(data = data?.get(item)) { id -> onClick(id) }

        }
    }

}

@Composable
fun KeywordCardItem(
    modifier: Modifier = Modifier,
    data: Genre? = null,
    onClick: (id: Int?) -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = secondaryColor),
        //   backgroundColor = secondaryColor,
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .clickable { onClick(data?.id) }
            .padding(4.dp),
        shape = RoundedCornerShape(50.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Text(
            text = data?.name ?: "------------",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = Color.White
        )
    }
}
