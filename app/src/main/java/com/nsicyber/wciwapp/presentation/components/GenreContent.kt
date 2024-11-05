package com.nsicyber.wciwapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nsicyber.wciwapp.data.remote.response.genreKeywordList.Genre

@Composable
fun GenreContent(genres: List<Genre?>){
    Column(
        modifier = Modifier.padding(
            start = 16.dp, top = 16.dp, bottom = 8.dp
        )
    ) {
        Text(
            text = "Genre",
            color = Color.Gray,
            fontSize = 22.sp,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.fillMaxWidth()
        )

        val genreText = genres.joinToString(", ") { it?.name.orEmpty() }

        Text(
            text = genreText,
            color = Color.White,
            fontSize = 28.sp,
            lineHeight = 30.sp,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
    }
}