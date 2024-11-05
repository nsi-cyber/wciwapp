package com.nsicyber.wciwapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nsicyber.wciwapp.common.Constants.IMAGE_URL
import com.nsicyber.wciwapp.domain.model.CardViewData


@Composable
fun RankedCard(
    modifier: Modifier = Modifier,
    position: Int? = null, data: CardViewData? = null, onItemClick: (Int) -> Unit = {}
) {

    Row(modifier = modifier
        .width(330.dp)

        .clickable {
            onItemClick(data?.id ?: -1)
        }


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
                text = "$position.",
                color = Color.White,
                fontSize = 28.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                modifier = modifier,
            )

            Text(
                maxLines = 2, overflow = TextOverflow.Ellipsis,
                text = data?.title.orEmpty(),
                color = Color.White,
                fontSize = 25.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Medium,
                modifier = modifier.width(190.dp)
            )

            Text(
                text = data?.date?.take(4).orEmpty(),
                color = Color.Gray,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Medium,
                modifier = modifier,
            )

        }

    }

}