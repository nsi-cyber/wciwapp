package com.nsicyber.wciwapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nsicyber.wciwapp.common.Constants
import com.nsicyber.wciwapp.data.remote.response.imageList.ImageListResponseItem
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.toggleScale
import net.engawapg.lib.zoomable.zoomable


@Composable
fun ImagesListContent(
    title: String? = "Popular",
    list: List<ImageListResponseItem?>?, onItemClick: (ImageListResponseItem?) -> Unit = {}
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
                ImageCard(
                    image = list?.get(image), onItemClick = onItemClick
                )


            }
        }
    }

}

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: ImageListResponseItem? = null, onItemClick: (image: ImageListResponseItem?) -> Unit = {}
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

                )
            ,
            contentScale = ContentScale.Fit,
        )


    }

}

