package com.nsicyber.wciwapp.presentation.personScreen

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nsicyber.wciwapp.R
import com.nsicyber.wciwapp.common.Constants
import com.nsicyber.wciwapp.common.Constants.IMAGE_URL
import com.nsicyber.wciwapp.data.remote.response.personImageList.Profile
import com.nsicyber.wciwapp.data.remote.response.personMovieCreditList.Cast
import com.nsicyber.wciwapp.data.remote.response.personMovieCreditList.Crew
import com.nsicyber.wciwapp.formatDate
import com.nsicyber.wciwapp.parseDateFromAnyFormat
import com.nsicyber.wciwapp.presentation.components.BaseView
import com.nsicyber.wciwapp.presentation.components.shimmerEffect
import com.nsicyber.wciwapp.primaryColor
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.toggleScale
import net.engawapg.lib.zoomable.zoomable


@Composable
fun PersonDetailScreen(
    personId: Int = 0,
    personDetailScreenViewModel: PersonDetailScreenViewModel = hiltViewModel<PersonDetailScreenViewModel>(),
    onMovieDetailClicked: (movieId: Int) -> Unit = {},
    onShowDetailClicked: (showId: Int) -> Unit = {},
) {
    val personDetailScreenState by personDetailScreenViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        personDetailScreenViewModel.onEvent(PersonDetailScreenEvent.LoadPersonDetailScreen(personId))
    }


    BaseView(isPageLoading = personDetailScreenState.isLoading, content = {
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

                        model = "$IMAGE_URL${personDetailScreenState.personDetail?.profile_path}",
                        contentDescription = personDetailScreenState.personDetail?.name,
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
                        text = stringResource(R.string.about_person),
                        color = Color.Gray,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        lineHeight = 30.sp,
                        text = personDetailScreenState.personDetail?.name ?: "",
                        color = Color.White,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = stringResource(R.string.know_for),
                        color = Color.Gray,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        lineHeight = 30.sp,
                        text = personDetailScreenState.personDetail?.known_for_department ?: "",
                        color = Color.White,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )

                    personDetailScreenState.personDetail?.biography?.takeIf { it.isNotEmpty() }
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
                        text = stringResource(R.string.birth_date),
                        color = Color.Gray,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = personDetailScreenState.personDetail?.birthday?.formatDate() ?: "",
                        color = Color.White,
                        fontSize = 28.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth()
                    )

                }
            }

            item {
                personDetailScreenState.personDetail?.deathday?.takeIf { it.isNotBlank() }?.let {
                    Column(
                        modifier = Modifier.padding(
                            start = 16.dp, top = 16.dp, bottom = 8.dp
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.death_date),
                            color = Color.Gray,
                            fontSize = 22.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = personDetailScreenState.personDetail?.deathday?.formatDate()
                                ?: "",
                            color = Color.White,
                            fontSize = 28.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )

                    }
                }
            }

            item {
                personDetailScreenState.images?.takeIf { it.isNotEmpty() }?.let {
                    PersonImagesListContent(
                        title = stringResource(R.string.images),
                        list = personDetailScreenState.images,
                    )
                }
            }

            item {
                personDetailScreenState.movies?.let { movies ->
                    movies.takeIf { it.cast.isNotEmpty() }?.let {
                        CastCreditListContent(
                            title = stringResource(R.string.appearances_in),
                            subtitle = stringResource(R.string.movies),
                            list = it.cast.sortedByDescending { it.release_date.parseDateFromAnyFormat() },
                            onItemClick = { movieId -> onMovieDetailClicked(movieId) })
                    }

                    movies.takeIf { it.crew.isNotEmpty() }?.let {
                        CrewCreditListContent(
                            title = stringResource(R.string.behind_the_camera),
                            subtitle = stringResource(R.string.movies),
                            list = it.crew.sortedByDescending { it.release_date.parseDateFromAnyFormat() },
                            onItemClick = { movieId -> onMovieDetailClicked(movieId) })
                    }


                }
            }

            item {
                personDetailScreenState.shows?.let { shows ->
                    shows.takeIf { it.cast.isNotEmpty() }?.let {
                        CastCreditListContent(
                            title = stringResource(R.string.appearances_in),
                            subtitle = stringResource(R.string.shows),
                            list = it.cast.sortedByDescending { it.first_air_date.parseDateFromAnyFormat() },
                            onItemClick = { showId -> onShowDetailClicked(showId) })
                    }

                    shows.takeIf { it.crew.isNotEmpty() }?.let {
                        CrewCreditListContent(
                            title = stringResource(R.string.behind_the_camera),
                            subtitle = stringResource(R.string.shows),
                            list = it.crew.sortedByDescending { it.first_air_date.parseDateFromAnyFormat() },
                            onItemClick = { showId -> onShowDetailClicked(showId) })
                    }


                }
            }


        }
    })


}


@Composable
fun PersonImagesListContent(
    title: String? = "",
    list: List<Profile?>?, onItemClick: (Profile?) -> Unit = {}
) {
    val lazyGridState = rememberLazyStaggeredGridState()

    Column {
        Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)) {

            Text(
                text = title ?: "",
                color = Color.White,
                fontSize = 28.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
        }

        LazyHorizontalStaggeredGrid(
            state = lazyGridState,
            rows = StaggeredGridCells.Fixed(1),
            modifier = Modifier.height(300.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalItemSpacing = 8.dp
        ) {

            items(list?.size ?: 0) { image ->
                PersonImageCard(
                    image = list?.get(image), onItemClick = onItemClick
                )


            }
        }
    }

}

@Composable
fun PersonImageCard(
    modifier: Modifier = Modifier,
    image: Profile? = null, onItemClick: (image: Profile?) -> Unit = {}
) {
    val zoomState = rememberZoomState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .fillMaxSize()
            .aspectRatio(((image?.aspect_ratio ?: 1.0)).toFloat())


    ) {


        AsyncImage(

            model = "${Constants.IMAGE_URL_ORIGINAL}${image?.file_path}",
            contentDescription = image?.file_path,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFB8B5B5))
                .zoomable(
                    enableOneFingerZoom = false,
                    zoomState = zoomState,
                    onDoubleTap = { position -> zoomState.toggleScale(5.0f, position) }

                ),
            contentScale = ContentScale.Fit,
        )


    }

}


@Composable
fun CrewCreditListContent(
    title: String?,
    subtitle: String?,
    list: List<Crew?>,
    onItemClick: (Int) -> Unit
) {
    val shimmerModifier: Modifier = Modifier.shimmerEffect()
    val lazyGridState = rememberLazyGridState()
    val rowCount = if (list.size >= 3) 3 else list.size
    Column {
        Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)) {
            Text(
                text = title.orEmpty(),
                color = Color.Gray,
                fontSize = 22.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = subtitle.orEmpty(),
                color = Color.White,
                fontSize = 28.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
        }

        LazyHorizontalGrid(
            state = lazyGridState,
            rows = GridCells.Fixed(rowCount),
            modifier = Modifier.height((rowCount*145).dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

                items(list ?: listOf()) { data ->
                    PersonCrewCreditCard(data = data, onItemClick = onItemClick)
                }

        }
    }
}

@Composable
fun CastCreditListContent(
    title: String?,
    subtitle: String?,
    list: List<Cast?>,
    onItemClick: (Int) -> Unit
) {
    val shimmerModifier: Modifier = Modifier.shimmerEffect()
    val lazyGridState = rememberLazyGridState()
    val rowCount = if (list.size >= 3) 3 else list.size

    Column {
        Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)) {
            Text(
                text = title.orEmpty(),
                color = Color.Gray,
                fontSize = 22.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = subtitle.orEmpty(),
                color = Color.White,
                fontSize = 28.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
        }

        LazyHorizontalGrid(
            state = lazyGridState,
            rows = GridCells.Fixed(rowCount),
            modifier = Modifier.height((rowCount*145).dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

                items(list ?: listOf()) { data ->
                    PersonCastCreditCard(data = data, onItemClick = onItemClick)
                }

        }
    }
}


@Composable
fun PersonCastCreditCard(
    modifier: Modifier = Modifier,
    data: Cast? = null, onItemClick: (Int) -> Unit = {}
) {

    Row(modifier = modifier
        .clickable {
            onItemClick(data?.id ?: -1)
        }
        .width(330.dp)



    ) {


        AsyncImage(

            model = "$IMAGE_URL${data?.poster_path}",
            contentDescription = data?.title,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(width = 100.dp, height = 125.dp)
                .aspectRatio(4 / 5f)
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))


        )
        Column(
            modifier = Modifier
                .height(125.dp)
                .padding(start = 8.dp)
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = data?.release_date?.take(4) ?: data?.first_air_date?.take(4) ?: "-",
                color = Color.White,
                fontSize = 28.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                modifier = modifier,
            )

            Text(
                maxLines = 2, overflow = TextOverflow.Ellipsis,
                text = data?.title ?: data?.name ?: "-",
                color = Color.White,
                fontSize = 25.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Medium,
                modifier = modifier.width(190.dp)
            )

            Text(
                maxLines = 1, overflow = TextOverflow.Ellipsis,
                text = data?.character.orEmpty(),
                color = Color.Gray,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Medium,
                modifier = modifier,
            )

        }

    }

}


@Composable
fun PersonCrewCreditCard(
    modifier: Modifier = Modifier,
    data: Crew? = null, onItemClick: (Int) -> Unit = {}
) {

    Row(modifier = modifier
        .clickable {
            onItemClick(data?.id ?: -1)
        }
        .width(330.dp)



    ) {


        AsyncImage(

            model = "$IMAGE_URL${data?.poster_path}",
            contentDescription = data?.title,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(width = 100.dp, height = 125.dp)
                .aspectRatio(4 / 5f)
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))


        )
        Column(
            modifier = Modifier
                .height(125.dp)
                .padding(start = 8.dp)
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = data?.release_date?.take(4) ?: data?.first_air_date?.take(4) ?: "-",
                color = Color.White,
                fontSize = 28.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                modifier = modifier,
            )

            Text(
                maxLines = 2, overflow = TextOverflow.Ellipsis,
                text = data?.title ?: data?.name ?: "-",
                color = Color.White,
                fontSize = 25.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Medium,
                modifier = modifier.width(190.dp)
            )

            Text(                maxLines = 1, overflow = TextOverflow.Ellipsis,
                text = data?.department.toString(),
                color = Color.Gray,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Medium,
                modifier = modifier,
            )

        }

    }

}