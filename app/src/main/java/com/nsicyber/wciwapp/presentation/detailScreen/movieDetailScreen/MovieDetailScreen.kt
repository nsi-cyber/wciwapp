package com.nsicyber.wciwapp.presentation.detailScreen.movieDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nsicyber.wciwapp.common.Constants
import com.nsicyber.wciwapp.common.Constants.IMAGE_URL
import com.nsicyber.wciwapp.formatDate
import com.nsicyber.wciwapp.presentation.components.BaseView
import com.nsicyber.wciwapp.presentation.components.CastListContent
import com.nsicyber.wciwapp.presentation.components.CrewListContent
import com.nsicyber.wciwapp.presentation.components.GenreContent
import com.nsicyber.wciwapp.presentation.components.ImagesListContent
import com.nsicyber.wciwapp.presentation.components.PaginationListContent
import com.nsicyber.wciwapp.presentation.components.ParentGuideView
import com.nsicyber.wciwapp.presentation.components.ProviderListContent
import com.nsicyber.wciwapp.primaryColor


@Composable
fun MovieDetailScreen(
    movieId: Int?,
    movieDetailScreenViewModel: MovieDetailScreenViewModel = hiltViewModel<MovieDetailScreenViewModel>(),
    onMovieDetailClicked: (movieId: Int) -> Unit = {},
) {
    val movieDetailScreenState by movieDetailScreenViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        movieDetailScreenViewModel.onEvent(MovieDetailEvent.LoadMovieDetailScreen(movieId))
    }


    BaseView(isPageLoading = movieDetailScreenState.isLoading, content = {
        LazyColumn(
            modifier = Modifier
                .background(primaryColor), contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(6 / 9f)
                ) {
                    AsyncImage(

                        model = "${IMAGE_URL}${movieDetailScreenState.details?.poster_path}",
                        contentDescription = movieDetailScreenState.details?.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .drawWithCache {
                                val gradient = Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, primaryColor),
                                    startY = size.height / 2,
                                    endY = size.height
                                )
                                onDrawWithContent {
                                    drawContent()
                                    drawRect(gradient)
                                }
                            }


                    )
                }
            }


            item {
                Column(
                    modifier = Modifier.padding(
                        start = 16.dp, top = 16.dp, bottom = 8.dp, end = 16.dp
                    )
                ) {
                    Text(
                        text = "About Movie",
                        color = Color.Gray,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        lineHeight = 30.sp,
                        text = movieDetailScreenState.details?.title ?: "",
                        color = Color.White,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )

                    movieDetailScreenState.details?.overview?.takeIf { it.isNotEmpty() }
                        ?.let { overview ->

                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = overview,
                                color = Color.White,
                                fontSize = 22.sp,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                }
            }


            item {
                Column(
                    modifier = Modifier.padding(
                        start = 16.dp, top = 16.dp, bottom = 8.dp
                    )
                ) {
                    Text(
                        text = "Release Date",
                        color = Color.Gray,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = movieDetailScreenState.details?.release_date?.formatDate() ?: "",
                        color = Color.White,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )

                }
            }

            item {
                movieDetailScreenState.details?.genres?.takeIf { it.isNotEmpty() }?.let { genres ->
                    GenreContent(genres)
                }
            }

            item {
                movieDetailScreenState.images?.takeIf { it.isNotEmpty() }?.let {
                    ImagesListContent(
                        title = "Images",
                        list = movieDetailScreenState.images,
                    )
                }
            }


            item {
                movieDetailScreenState.credits?.cast?.takeIf { it.isNotEmpty() }?.let {
                    CastListContent(it)


                }
            }


            item {
                movieDetailScreenState.credits?.crew?.takeIf { it.isNotEmpty() }?.let {
                    CrewListContent(it)
                }
            }

            item {
                movieDetailScreenState.parentalGuide?.takeIf { it.categoryList.isNotEmpty() }?.let {
                    ParentGuideView(it)
                }
            }

            item {
                movieDetailScreenState.watchProviders?.getProviderByCountryCode(Constants.BASE_COUNTRY)?.flatrate?.takeIf { it.isNotEmpty() }
                    ?.let { list ->
                        ProviderListContent(list = list)
                    }
            }

            item {
                movieDetailScreenState.similars?.takeIf { it.isNotEmpty() }?.let {
                    PaginationListContent(title = "Similar",
                        subtitle = "Movies",
                        rowCount = 1,
                        list = movieDetailScreenState.similars,
                        onItemClick = { id ->
                            onMovieDetailClicked(id)
                        })
                }
            }


        }
    })


}



