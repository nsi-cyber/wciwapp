package com.nsicyber.wciwapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.nsicyber.wciwapp.R
import com.nsicyber.wciwapp.primaryColor

@Composable
fun BaseView(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
    isPageLoading: Boolean = false,
) {


    Box(modifier = Modifier.fillMaxSize()) {


        content()

        if (isPageLoading) {
            LoadingScreen()
        }


    }


}


@Preview
@Composable
fun LoadingScreen() {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.popcorn_loading
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryColor),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .clip(
                    RoundedCornerShape(20.dp)
                )
                .background(Color.Black)


        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieAnimation(
                    composition = preloaderLottieComposition,
                    progress = preloaderProgress,
                    modifier = Modifier.size(200.dp)
                )
                Text(
                    text = stringResource(R.string.please_wait),
                    color = Color.Gray,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                )
            }
        }
    }
}