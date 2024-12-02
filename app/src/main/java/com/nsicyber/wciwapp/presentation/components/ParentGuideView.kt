package com.nsicyber.wciwapp.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nsicyber.wciwapp.R
import com.nsicyber.wciwapp.domain.model.ParentalGuideCategoryItem
import com.nsicyber.wciwapp.domain.model.ParentalGuideCategoryList
import com.nsicyber.wciwapp.domain.model.toColor
import com.nsicyber.wciwapp.domain.model.toText


@Composable
fun ParentGuideListItemView(
    data: ParentalGuideCategoryItem
) {
    val context= LocalContext.current
    val isSelected = remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (isSelected.value) 180f else 0f,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing),
        label = ""
    )
    Column(
        modifier =
        Modifier.animateContentSize(
            animationSpec = tween(
                durationMillis = 50, easing = FastOutSlowInEasing
            )
        )
    ) {
        Box(
            Modifier
                .clickable { isSelected.value = !isSelected.value }
                .padding(16.dp)
        ) {

            Column {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxHeight()
                ) {

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .height(44.dp)
                            .width(6.dp)
                            .background(data.severityType?.toColor() ?: Color.Gray)
                    )
                    Column {
                        Text(
                            text = data.categoryType?.toText(context).toString(),
                            color = Color.White,
                            fontSize = 22.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold,
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = data.severityType?.toText(context).toString(),
                                color = Color.Gray,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Normal,
                            )
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = "(%${data.severityPercentage})",
                                color = Color.Gray,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Normal,
                            )
                            data.userComments.takeIf { it.isNotEmpty() }?.let {
                                Spacer(
                                    Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                )
                                Text(
                                    text = stringResource(R.string.comments),
                                    color = Color.Gray,
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Normal,
                                )
                                Icon(
                                    modifier = Modifier.rotate(rotationAngle),
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "",
                                    tint = Color.White
                                )
                            }
                        }
                    }

                    Spacer(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                }

                if (isSelected.value) {
                    data.userComments.forEach { comment ->
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(0.6.dp)
                                    .background(Color.Gray)
                            )
                            Text(
                                text = comment.toString(),
                                color = Color.White,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier.fillMaxWidth()
                            )


                        }
                    }


                }

            }

        }


    }
}


@Preview
@Composable
fun ParentGuideView(data: ParentalGuideCategoryList = ParentalGuideCategoryList(listOf())) {
    Column {
        Column(
            modifier = Modifier.padding(
                start = 16.dp,
                top = 16.dp,
                bottom = 8.dp, end = 16.dp
            )
        ) {
            Text(
                text = stringResource(R.string.parent),
                color = Color.Gray,
                fontSize = 22.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                lineHeight = 30.sp,
                text = stringResource(R.string.guide),
                color = Color.White,
                fontSize = 28.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

        }

        data.categoryList.forEach { parentalCategory ->
            ParentGuideListItemView(parentalCategory)

        }


    }
}


