package com.nsicyber.wciwapp.presentation.detailScreen.movieDetailScreen

import android.content.res.ColorStateList
import android.content.res.Resources
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.nsicyber.wciwapp.R
import com.nsicyber.wciwapp.common.Constants.IMAGE_URL
import com.nsicyber.wciwapp.primaryColor
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.launch

@Composable
fun getScreenWidthDp(): Dp {
    val displayMetrics = Resources.getSystem().displayMetrics
    val screenWidthPx = displayMetrics.widthPixels
    val density = displayMetrics.density
    return (screenWidthPx / density).dp
}

@Composable
fun VideoPosterView(
    poster: String,
    trailer: String?,
) {
    val screenWidth = getScreenWidthDp()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val isPlaying = remember { mutableStateOf(false) }
    val isVideoReady = remember { mutableStateOf(false) }
    val isBuffering = remember { mutableStateOf(false) }
    val youTubePlayerState = remember { mutableStateOf<YouTubePlayer?>(null) }
    val infiniteTransition = rememberInfiniteTransition(label = "Infinite Color Animation")
    val degrees by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Infinite Colors"
    )

    Box(
        modifier = Modifier
            .background(primaryColor)
            .fillMaxWidth()
            .height((screenWidth / 2) * 3)
    ) {

        AsyncImage(
            model = "$IMAGE_URL${poster}",
            contentDescription = "Poster Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier .clickable {
                if (!isPlaying.value) {
                    scope.launch {
                        isPlaying.value = true
                        if (isVideoReady.value == false && !trailer.isNullOrBlank())
                            youTubePlayerState.value?.loadVideo(trailer, 0f)
                        else
                            youTubePlayerState.value?.play()
                    }
                } else {
                    scope.launch {
                        youTubePlayerState.value?.pause()
                        isPlaying.value = false
                    }
                }
            }
                .background(primaryColor)
                .fillMaxWidth()
                .height((screenWidth / 2) * 3)

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
                }
        )
        trailer?.let {
            AndroidView(
                factory = {
                    YouTubePlayerView(context).apply {
                        this.backgroundTintList = ColorStateList.valueOf(primaryColor.toArgb())
                        this.enableAutomaticInitialization = true

                        this.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {

                                youTubePlayerState.value = youTubePlayer
                                youTubePlayer.cueVideo(trailer, 0f)
                            }

                            override fun onStateChange(
                                youTubePlayer: YouTubePlayer,
                                state: PlayerConstants.PlayerState
                            ) {
                                if (state == PlayerConstants.PlayerState.PLAYING) {
                                    isPlaying.value = true
                                    isVideoReady.value = true
                                    isBuffering.value = false
                                } else if (state == PlayerConstants.PlayerState.BUFFERING) {
                                    isBuffering.value = true
                                }
                            }
                        })
                    }
                },
                modifier = Modifier
                    .drawWithCache {
                        val gradient = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, primaryColor),
                            startY = size.height / 1.5f,
                            endY = size.height
                        )
                        onDrawWithContent {

                            drawContent()
                            drawRect(gradient)

                        }
                    }
                    .alpha(if (isPlaying.value && isVideoReady.value) 1f else 0.000001f) .width(screenWidth)
                    .height(((screenWidth / 2) * 3))
                    .graphicsLayer(scaleX = 3.7f, scaleY = 3.7f)

            )


            Column(
                modifier = Modifier.align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Icon(
                    painter = painterResource(if (isPlaying.value) R.drawable.ic_pause else R.drawable.ic_play),
                    contentDescription = "Start Stop",
                    modifier = Modifier
                        .size(36.dp)
                        .drawWithContent {
                            rotate(degrees = degrees) {

                                if (isBuffering.value)
                                    drawCircle(
                                        brush = Brush.sweepGradient(
                                            listOf(
                                                primaryColor,
                                                Color.White
                                            )
                                        ),
                                        radius = 60f,
                                        blendMode = BlendMode.SrcIn,
                                    )
                            }
                            drawContent()
                        }
                )

                Text(
                    text = if (isPlaying.value) stringResource(R.string.pause) else stringResource(R.string.watch), color = Color.Gray,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                )
                Text(
                    text = stringResource(R.string.trailer), color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}


