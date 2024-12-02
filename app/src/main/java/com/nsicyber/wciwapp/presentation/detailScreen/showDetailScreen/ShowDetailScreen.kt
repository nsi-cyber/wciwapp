package com.nsicyber.wciwapp.presentation.detailScreen.showDetailScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nsicyber.wciwapp.R
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
import com.nsicyber.wciwapp.presentation.detailScreen.movieDetailScreen.getProviderByCountryCode
import com.nsicyber.wciwapp.primaryColor
import com.nsicyber.wciwapp.secondaryColor


@Composable
fun ShowDetailScreen(
    showId: Int?,
    showDetailScreenViewModel: ShowDetailScreenViewModel = hiltViewModel<ShowDetailScreenViewModel>(),
    onShowDetailClicked: (showId: Int) -> Unit = {},
) {
    val showDetailScreenState by showDetailScreenViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        showDetailScreenViewModel.onEvent(ShowDetailEvent.LoadShowDetailScreen(showId))
    }


    BaseView(isPageLoading = showDetailScreenState.isLoading, content = {
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

                        model = "$IMAGE_URL${showDetailScreenState.details?.poster_path}",
                        contentDescription = showDetailScreenState.details?.name,
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
                        start = 16.dp,
                        top = 16.dp,
                        bottom = 8.dp, end = 16.dp
                    )
                ) {
                    Text(
                        text = stringResource(R.string.about_show),
                        color = Color.Gray,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        lineHeight = 30.sp,
                        text = showDetailScreenState.details?.name ?: "",
                        color = Color.White,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )

                    showDetailScreenState.details?.overview?.takeIf { it.isNotEmpty() }
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
                        start = 16.dp,
                        top = 16.dp,
                        bottom = 8.dp
                    )
                ) {
                    Text(
                        text = stringResource(R.string.release_date),
                        color = Color.Gray,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = (showDetailScreenState.details?.first_air_date?.formatDate() + " - " + (showDetailScreenState.details?.last_air_date?.formatDate()
                            ?: "...")),
                        color = Color.White,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )

                }
            }

            item {
                showDetailScreenState.details?.genres?.takeIf { it.isNotEmpty() }?.let { genres ->
                    GenreContent(genres)
                }
            }

            item {
                showDetailScreenState.images?.takeIf { it.isNotEmpty() }?.let {
                    ImagesListContent(
                        title = stringResource(R.string.images),
                        list = showDetailScreenState.images,
                    )
                }
            }

            item {
                showDetailScreenState.credits?.cast?.takeIf { it.isNotEmpty() }?.let {
                    CastListContent(it)
                }
            }


            item {
                showDetailScreenState.credits?.crew?.takeIf { it.isNotEmpty() }?.let {
                    CrewListContent(it)
                }
            }



            item {
                showDetailScreenState.parentalGuide?.takeIf { it.categoryList.isNotEmpty() }?.let {
                    ParentGuideView(it)
                }
            }

            item {
                showDetailScreenState.seasonList?.takeIf { it.isNotEmpty() }?.let {
                    SeasonDetailView(
                        data = showDetailScreenState.seasonList,
                        totalSeasons = showDetailScreenState.details?.number_of_seasons ?: 0,
                        getData = {
                            showDetailScreenViewModel.onEvent(ShowDetailEvent.GetShowSeasonDetail(it))
                        })
                }
            }

            item {
                showDetailScreenState.watchProviders?.getProviderByCountryCode(Constants.BASE_COUNTRY)?.flatrate?.takeIf { it.isNotEmpty() }
                    ?.let { list ->
                        ProviderListContent(list = list)
                    }
            }





            item {
                showDetailScreenState.similars?.takeIf { it.isNotEmpty() }?.let {
                    PaginationListContent(title = stringResource(R.string.similar), subtitle = stringResource(R.string.shows), rowCount = 1,
                        list = showDetailScreenState.similars,
                        onItemClick = { id ->
                            onShowDetailClicked(id)
                        })
                }
            }


        }
    })


}

@Composable
fun SeasonDetailView(
    totalSeasons: Int? = 12,
    data: List<SeasonModel?>?,
    getData: (seasonNumber: Int) -> Unit = {}
) {
    var selectedSeason by remember { mutableStateOf(1) }
    var seasonEpisodesDetail by remember { mutableStateOf(false) }

    LaunchedEffect(selectedSeason) {
        if (data?.find { it?.seasonNumber == selectedSeason } == null) {
            getData(selectedSeason)
        }
    }

    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp)) {
            Text(
                text = stringResource(R.string.all),
                color = Color.Gray,
                fontSize = 22.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = stringResource(R.string.seasons),
                color = Color.White,
                fontSize = 28.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
        }
        SeasonSelector(
            totalSeasons = totalSeasons,
            selectedSeason = selectedSeason,
            onSeasonSelected = { selectedSeason = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SeasonDetailContent(
            seasonData = data?.find { it?.seasonNumber == selectedSeason },
            seasonEpisodesDetail = seasonEpisodesDetail,
            onEpisodesToggle = { seasonEpisodesDetail = !seasonEpisodesDetail }
        )
    }
}


@Composable
fun SeasonSelector(
    totalSeasons: Int?,
    selectedSeason: Int,
    onSeasonSelected: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
    ) {
        items(totalSeasons ?: 0) { season ->
            val seasonNumber = season + 1
            val isSelected = seasonNumber == selectedSeason
            SeasonCard(
                seasonNumber = seasonNumber,
                isSelected = isSelected,
                onClick = { onSeasonSelected(seasonNumber) }
            )
        }
    }
}

@Composable
fun SeasonCard(
    seasonNumber: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = secondaryColor),
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        border = if (isSelected) BorderStroke(1.dp, Color.White) else null,
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.season), color = Color.White)
            Text(
                text = "$seasonNumber",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun SeasonDetailContent(
    seasonData: SeasonModel?,
    seasonEpisodesDetail: Boolean,
    onEpisodesToggle: () -> Unit
) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (seasonEpisodesDetail) 180f else 0f,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    Column(
        modifier = Modifier
            .animateContentSize(animationSpec = tween(durationMillis = 300))
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        seasonData?.seasonName?.takeIf { it.isNotEmpty() }?.let {
            Text(text = it, color = Color.White, modifier = Modifier.padding(bottom = 8.dp))
        }
        seasonData?.seasonOverview?.takeIf { it.isNotEmpty() }?.let {
            Text(text = it, color = Color.White, modifier = Modifier.padding(bottom = 8.dp))
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(onClick = onEpisodesToggle)
                .clip(RoundedCornerShape(12.dp))
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = stringResource(R.string.episodes, seasonData?.episodes?.size ?: 0),
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.rotate(rotationAngle),
                tint = Color.White
            )
        }

        if (seasonEpisodesDetail) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                seasonData?.episodes?.forEachIndexed { index, episodeModel ->
                    EpisodeListItemView(count = index + 1, data = episodeModel)
                }
            }
        }
    }
}

@Composable
fun EpisodeListItemView(
    count: Int,
    data: EpisodeModel?
) {
    var isSelected by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (isSelected) 180f else 0f,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    Column(
        modifier = Modifier
            .clickable { isSelected = !isSelected }
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(secondaryColor)
            .padding(12.dp)
            .animateContentSize(animationSpec = tween(durationMillis = 300))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.episode, count, data?.episodeName?:""),
                modifier = Modifier.weight(1f),
                color = Color.White
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.rotate(rotationAngle),
                tint = Color.White
            )
        }

        if (isSelected) {
            Row(modifier = Modifier.padding(top = 8.dp)) {
                AsyncImage(
                    model = "$IMAGE_URL${data?.imageUrl}",
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                        .weight(0.4f)
                        .aspectRatio(6 / 9f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = data?.episodeOverview ?: "",
                    modifier = Modifier.weight(0.6f),
                    color = Color.White
                )
            }
        }
    }
}
