package com.nsicyber.wciwapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nsicyber.wciwapp.R
import com.nsicyber.wciwapp.common.Constants.IMAGE_URL_PROFILE
import com.nsicyber.wciwapp.data.remote.response.creditsList.Crew

@Composable
fun CrewListContent(
    list: List<Crew?>?
) {
    val lazyGridState = rememberLazyGridState()

    Column {
        Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)) {
            Text(
                text = stringResource(R.string.crew),
                color = Color.White,
                fontSize = 28.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
        }

        LazyHorizontalGrid(
            state = lazyGridState,
            rows = GridCells.Fixed(1),
            modifier = Modifier.height(240.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            items(list?.sortedByDescending { it?.popularity } ?: listOf()) { crew ->
                CrewCard(
                    crew = crew
                )


            }

        }
    }

}

@Composable
fun CrewCard(
    modifier: Modifier = Modifier,
    crew: Crew? = null
) {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(120.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        AsyncImage(
            model = "$IMAGE_URL_PROFILE${crew?.profile_path}",
            contentDescription = crew?.name,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(120.dp)
                .shadow(5.dp, RoundedCornerShape(99.dp))
                .clip(RoundedCornerShape(99.dp))
                .background(Color(0xFFB8B5B5))
                .aspectRatio(1f)
        )


        Text(
            text = crew?.name ?: "",
            color = Color.White,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = crew?.job ?: "",
            color = Color.LightGray,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )


    }

}
