package com.nsicyber.wciwapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nsicyber.wciwapp.domain.model.CardViewData
import presentation.components.AutoScrollerHorizontalPagerView


@Composable
fun TrendingSection(
    trending: List<CardViewData?>,
    onSearchClicked: () -> Unit,
    onMovieDetailClicked: (movieId: Int?) -> Unit,
    onShowDetailClicked: (showId: Int?) -> Unit,
) {
    Box(modifier = Modifier.padding(bottom = 24.dp)) {
        AutoScrollerHorizontalPagerView(list = trending) {
            if (it.media_type == "movie") {
                onMovieDetailClicked(it.id)
            } else if (it.media_type == "tv") {
                onShowDetailClicked(it.id)
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .shadow(4.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .align(Alignment.BottomCenter)
                .clickable {
                    onSearchClicked()
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Discover Movies & Shows...",
                    color = Color.Black,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.wrapContentSize()
                )
            }
        }
    }
}