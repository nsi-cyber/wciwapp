package com.nsicyber.wciwapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nsicyber.wciwapp.domain.model.CardViewData


@Composable
fun PaginationListContent(
    title: String? = "",
    subtitle: String? = "",
    rowCount: Int = 2,
    list: List<CardViewData?>?,
    onItemClick: (Int) -> Unit,
    pagination: () -> Unit? = { null }
) {
    val lazyGridState = rememberLazyGridState()

    Column {
        Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)) {
            Text(
                text = title ?: "",
                color = Color.Gray,
                fontSize = 22.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = subtitle ?: "",
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
            modifier = Modifier.height(if (rowCount == 2) 400.dp else 200.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            items(list ?: listOf()) { movie ->
                MovieCard(movie = movie, onItemClick = onItemClick)
                lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index?.let {
                    if (it + 10 > (list?.size ?: 0)) {
                        LaunchedEffect(Unit) {
                            pagination()
                        }
                    }

                }
            }
        }
    }
}
