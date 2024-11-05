package com.nsicyber.wciwapp.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Preview
@Composable
fun BottomNavigation(
    navigationActions: NavigationActions = NavigationActions(
        NavHostController(
            LocalContext.current
        )
    ),
    isMenuShow: MutableState<Boolean> = mutableStateOf(false),
    currentDestination: String? = "",
    onMenuChanged: (bool: Boolean) -> Unit = {}
) {
    val context = LocalContext.current
    val clickedModifier = Modifier
        .border(
            width = 1.5.dp,
            color = Color(0xFF2F80ED),
            shape = RoundedCornerShape(size = 12.dp)
        )
        .clip(shape = RoundedCornerShape(size = 12.dp))
        .background(color = Color(0xFFDBE9FC))



    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedVisibility(visible = isMenuShow.value) {
            Column(
                horizontalAlignment = Alignment.Start,
            ) {


            }
        }
    }
}
