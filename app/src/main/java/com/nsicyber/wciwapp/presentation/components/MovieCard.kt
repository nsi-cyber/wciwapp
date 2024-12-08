package com.nsicyber.wciwapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nsicyber.wciwapp.common.Constants.IMAGE_URL
import com.nsicyber.wciwapp.domain.model.CardViewData
import com.nsicyber.wciwapp.primaryColor


@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    movie: CardViewData? = null, onItemClick: (Int) -> Unit = {}
) {

    Box(contentAlignment = Alignment.Center,
        modifier = modifier    .clickable {
            onItemClick(movie?.id ?: -1)
        }
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(4 / 5f)



    ) {


        AsyncImage(

            model = "$IMAGE_URL${movie?.poster_path}",
            contentDescription = movie?.title,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFB8B5B5))
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
                })

        Text(
            text = movie?.title.orEmpty(),
            color = Color.White,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            modifier = modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )


    }

}
